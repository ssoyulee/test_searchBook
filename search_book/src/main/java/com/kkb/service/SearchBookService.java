package com.kkb.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Collections;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.kkb.code.ApiInfo;
import com.kkb.db.User;
import com.kkb.dto.SearchBookDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchBookService {

	@Autowired
	private UserSearchHistService userSearchHistService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private MostSearchWordService mostSearchWordService;
	
	/**
	 * 도서 검색을 진행한다.
	 * 
	 * @param dto
	 * @return
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 */
	public String searchBook( SearchBookDto dto ) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		
		String result = kakaoSearchBook( dto );
		try {
			// 이력을 등록한다. 이력 등록에 실패하였다고 오류를 발생시키지는 않는다.
			saveUserSearchHist(dto);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	/**
	 * HTTPS Clinet
	 * 
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
				.loadTrustMaterial(null, acceptingTrustStrategy)
				.build();

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(csf)
				.build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;
	}
	
	/**
	 * 카카오 도서 검색
	 * @param dto
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws KeyManagementException 
	 */
	public String kakaoSearchBook( SearchBookDto dto ) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		
		RestTemplate restTemplate = getRestTemplate();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl( ApiInfo.KAKAO_API.getUrl() )
                .queryParam("query", dto.getQuery() );
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", "KakaoAK " + ApiInfo.KAKAO_API.getKey());

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> result = restTemplate.exchange(uriBuilder.build().toUri(), HttpMethod.GET, entity, String.class);
		
		log.info("kakao search response => {}", result);
		
		return result.toString();
		
	}
	
	/**
	 * 개인 검색 이력을 등록한다.
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean saveUserSearchHist( SearchBookDto dto ) throws Exception {
		
		log.info("userId => {}", dto.getUserId() );
		log.info("query => {}", dto.getQuery() );
		
		String userId = dto.getUserId();
		String query = dto.getUserId();
		
		User user = userInfoService.getUserInfo( userId );
		// SEQ는 재사용을 위해 나누기 10 처리 한다.
		int lastSeq = user.getLastSearrchSeq()+1;
		if ( lastSeq > 0 ) {
			lastSeq = lastSeq % 10;
		}
		
		// 이력 등록
		if ( !userSearchHistService.insertSearchHist( userId, lastSeq, query ) ) {
			throw new Exception("이력 등록에 실패하였습니다.");
		}
		
		// 마지막 검색 SEQ 업데이트 (여기서 관리한 SEQ를 가지고 사용자당 10개씩만 관리한다. 
		if ( !userInfoService.updateUser( userId, lastSeq) ){
			throw new Exception("사용자 마지막 이력 등록에실패하였습니다.");
		}
		
		// 실시간 검색어를 카운팅 한다.
		mostSearchWordService.updateSearchCout (query );
		return true;
		
	}
	
	
	
}

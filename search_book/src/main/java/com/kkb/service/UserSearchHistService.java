package com.kkb.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kkb.dao.UserSearchHistDao;
import com.kkb.db.User;
import com.kkb.db.UserSearchHist;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserSearchHistService {

	@Autowired
	private UserSearchHistDao userSearchHistDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 검색 이력을 저장한다.
	 * @param userId
	 * @param seq
	 * @param query
	 * @return
	 */
	public boolean insertSearchHist( String userId, int seq, String query ) {
		
		log.debug("insertSearchHist > {},{},{}", userId,seq,query);
		
		UserSearchHist searchHist = UserSearchHist.builder().userId( userId ).seq( seq ).searchWord( query ).build();
		if ( userSearchHistDao.save(searchHist) == null ) {
			log.error("검색 이력 저장에 실패하였습니다. => {}",searchHist);
			return false;
		}
		return true;
		
	}
	
	/**
	 * 검색 이력을 가져온다.
	 * @param userId
	 * @return
	 */
	public List<UserSearchHist> selectUserSearchHist( String userId ){
		
		List<UserSearchHist> selectList = userSearchHistDao.findAll();
		User user = userInfoService.getUserInfo(userId);
		
		// 현재 등록된 이력을 마이너스 해서 음수가 나올경우 10을 더한다.
		selectList.forEach( hist -> { 
			
			int seq = hist.getPk().getSeq() - user.getLastSearrchSeq();
			seq = (seq <= 0) ? seq + 10 : seq;
			
			hist.getPk().setSeq( seq );
			
		});
		
		return selectList.stream()
				.sorted(Comparator.comparingInt(UserSearchHist::getSeq)
						.reversed())
						.collect(Collectors.toList());

	}
}

package com.kkb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kkb.dao.MostSearchWordDao;
import com.kkb.db.MostSearchWord;
import com.kkb.utils.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MostSearchWordService {

	private static final int START_COUNT = 1;
	
	@Autowired
	private MostSearchWordDao mostSearchWordDao;
	
	/**
	 * 실시간 검색를 위해 검색 이력을 추가한다.
	 * @param userId
	 * @param seq
	 * @param query
	 * @return
	 * @throws Exception 
	 */
	public void updateSearchCout( String query ) throws Exception {
		
		log.debug("updateSearchCout > {}",query);
		
		MostSearchWord searchWord = selectMostSearchWord(query);
		if ( CommonUtils.isEmpty( searchWord ) ) {
			insertMostSearchWord(query);
		} else {
			mostSearchWordDao.updateSearchCount(query);
		}
	}
	
	/**
	 * 검색어를 신규 등록한다.
	 * @param query
	 * @return
	 * @throws Exception 
	 */
	public void insertMostSearchWord( String query ) throws Exception {
		
		log.debug("insertMostSearchWord > {}",query);
		
		if ( mostSearchWordDao.save( MostSearchWord.builder().searhWord(query).searchCount(START_COUNT).build() ) == null ) {
			throw new Exception("등록 중 오류가 발생하였습니다.");
		}
	}
	
	/**
	 * 검색어 수를 증가시킨다.
	 * @param query
	 * @return
	 */
	public void updateMostSearchWord( String query ) {

		log.debug("updateMostSearchWord > {}",query);
		
		mostSearchWordDao.updateSearchCount(query);
	}
	
	/**
	 * 저장된 검색어가 존재하는지 확인한다.
	 * @param query
	 * @return
	 */
	public MostSearchWord selectMostSearchWord( String query ) {
		
		log.debug("selectMostSearchWord > {}",query);
		
		return mostSearchWordDao.selectUserBySearhWord(query);
	}
	
	
	/**
	 * 실시간 검색어를 가져온다.
	 * @return
	 */
	public List<MostSearchWord> selectMostSearchWordList(){
		
		return mostSearchWordDao.findTop10ByOrderBySearchCountDesc();

	}
	
}

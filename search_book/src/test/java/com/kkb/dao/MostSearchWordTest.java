package com.kkb.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkb.db.MostSearchWord;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MostSearchWordTest {

    @Autowired
    private MostSearchWordDao mostSearchWordDao;

    @Test
    public void testMostSearchWordRepository(){

    	MostSearchWord word = MostSearchWord.builder().searhWord("sw_1").searchCount(1).build();
    	MostSearchWord word1 = MostSearchWord.builder().searhWord("sw_1").searchCount(2).build();
    	
    	MostSearchWord word2 = MostSearchWord.builder().searhWord("sw_2").searchCount(1).build();
    	
    	MostSearchWord word3 = MostSearchWord.builder().searhWord("sw_3").searchCount(3).build();
    	MostSearchWord word4 = MostSearchWord.builder().searhWord("sw_4").searchCount(3).build();
    	
    	mostSearchWordDao.save( word );
    	mostSearchWordDao.save( word1 );
    	mostSearchWordDao.save( word2 );
    	mostSearchWordDao.save( word3 );
    	mostSearchWordDao.save( word4 );
    	
    	List<MostSearchWord> searchWordList = mostSearchWordDao.findAll(new Sort(Sort.Direction.DESC, "searchCount"));
    	for ( MostSearchWord sw : searchWordList ) {
    		log.info(sw.toString());
    	}
    	
		List<MostSearchWord> selectList = mostSearchWordDao.findTop10ByOrderBySearchCountDesc();
    	for ( MostSearchWord sw : selectList ) {
    		log.info(sw.toString());
    	}
		
    }
}

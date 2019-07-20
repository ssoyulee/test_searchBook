package com.kkb.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkb.db.UserSearchHist;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserSearchHistTest {

    @Autowired
    private UserSearchHistDao userSearchHistDao;

    @Test
    public void testUserSearchHistRepository(){
    	
    	UserSearchHist hist1 = UserSearchHist.builder().userId("tom").seq(1).searchWord("searchkey1").build();
//    	UserSearchHist hist2 = UserSearchHist.builder().userId("tom").seq(1).searchWord("searchkey2").build();
    	UserSearchHist hist2 = UserSearchHist.builder().userId("tom").seq(2).searchWord("searchkey2").build();
    	UserSearchHist hist3 = UserSearchHist.builder().userId("tom").seq(3).searchWord("searchkey3").build();
    	
    	userSearchHistDao.save( hist1 );
    	userSearchHistDao.save( hist2 );
    	userSearchHistDao.save( hist3 );
    	
    	List<UserSearchHist> searchHistList = userSearchHistDao.findAll( new Sort(Sort.Direction.DESC, "pk.seq") );
    	for ( UserSearchHist sh : searchHistList ) {
    		log.info(sh.toString());
    	}
    }
}

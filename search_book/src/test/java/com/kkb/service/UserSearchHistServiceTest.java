package com.kkb.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkb.dao.UserInfoDao;
import com.kkb.db.User;
import com.kkb.db.UserSearchHist;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSearchHistServiceTest {

    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    private UserSearchHistService userSearchHistService;
    
    @Test
    public void searchTest(){
    	
    	String userId = "tom";
    	User user = User.builder().userId(userId).userPw("111").lastSearrchSeq(3).build();
    	userInfoDao.save(user);
    	
    	userSearchHistService.insertSearchHist( userId, 1, "검색어1");
    	userSearchHistService.insertSearchHist( userId, 2, "검색어2");
    	userSearchHistService.insertSearchHist( userId, 3, "검색어3");
    	userSearchHistService.insertSearchHist( userId, 4, "검색어4");
    	userSearchHistService.insertSearchHist( userId, 5, "검색어5");
    	userSearchHistService.insertSearchHist( userId, 6, "검색어6");
    	userSearchHistService.insertSearchHist( userId, 7, "검색어7");
    	List<UserSearchHist> resultList = userSearchHistService.selectUserSearchHist( userId );
    	resultList.forEach(System.out::println);
    }
    
    
}

package com.kkb.dao;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkb.db.User;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserInfoDaoTest {

    @Autowired
    private UserInfoDao userInfoDao;

    @Test
    public void testUserRepository(){
    	
    	User user = User.builder().userId("tom").userPw("111").build();
    	User user1 = User.builder().userId("john").userPw("1111").build();
    	
    	assertNotNull( userInfoDao.save(user) );
    	userInfoDao.save(user1);
    	
        List<User> userList = userInfoDao.findAll();
        
        User tom = userList.get(0);
        assertThat(tom.getUserId(), is("tom"));
        assertThat(tom.getUserPw(), is("111"));
        
        User john = userInfoDao.selectUserById("john");
        assertThat(john.getUserId(), is("john"));
        
        userInfoDao.updateLastSearchSeq("tom", 1);
        
        userList = userInfoDao.findAll();
        for ( User us : userList ) {
        	log.info(us.toString());
        }
        
        
    }

}

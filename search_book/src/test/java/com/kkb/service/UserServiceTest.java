package com.kkb.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkb.dto.UserInfoDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserInfoService userInfoService;
    
    @Test
    public void testPasswordEncode() throws Exception {
    	
    	// 패스워드 암호화 테스트
    	String encode1 = userInfoService.encodingPassword("password");
    	String encode2 = userInfoService.encodingPassword("password");
    	
    	assertTrue( userInfoService.checkedPassword("password", encode1) );
    	assertTrue( userInfoService.checkedPassword("password", encode2) );
    	
    	// 로그인 테스트
    	UserInfoDto userInfo = new UserInfoDto();
    	userInfo.setUserId("userId");
    	userInfo.setUserPw("password");
    	
    	// 사용자등록
    	assertTrue( userInfoService.registUser(userInfo) );
    	
    	// 로그인성공
    	assertTrue( userInfoService.checkLoginUser(userInfo) );
    	
    	// 로그인실패
    	userInfo.setUserPw("1111");
    	assertFalse( userInfoService.checkLoginUser(userInfo) );
//        assertThat( encode1, is(encode2));
        
    }
    
}

package com.kkb.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkb.dto.SearchBookDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchBookServiceTest {

    @Autowired
    private SearchBookService searchBookService;
    
    @Test
    public void searchTest(){
    	SearchBookDto sb = new SearchBookDto();
    	sb.setQuery("탈무드");
    	sb.setPage(1);
    	sb.setSize(10);
    	try {
			Map map = searchBookService.kakaoSearchBook(sb);
			System.out.println( map.size() );
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}

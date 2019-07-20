package com.kkb.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kkb.dto.SearchBookDto;
import com.kkb.dto.UserInfoDto;
import com.kkb.service.MostSearchWordService;
import com.kkb.service.SearchBookService;
import com.kkb.service.UserInfoService;
import com.kkb.service.UserSearchHistService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/searchBook")
public class SearchBookController {

	@Autowired
	private SearchBookService searchBookService;
	
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private MostSearchWordService mostSearchWordService;

	@Autowired
	private UserSearchHistService userSearchHistService;
	
	@RequestMapping("/main")
	public String main() {
		
		log.info( "main" );
		
		return "main";
	}
	
	@RequestMapping("/loginUser")
	public String loginUser( @RequestBody UserInfoDto dto ) {
		
		log.info( "loginUser => {}", dto );
		
		try {
			userInfoService.checkLoginUser( dto );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "main";
	}
	
	@RequestMapping("/registUser")
	public String registUser( @RequestBody UserInfoDto dto ) {
		
		log.info( "registUser => {}", dto );
		
		try {
			userInfoService.registUser( dto );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "main";
	}
	
	@RequestMapping("/searchBook")
	public String searchBook( @RequestBody SearchBookDto dto ) {
		
		log.info( "searchBook => {}", dto );
		
		try {
			searchBookService.searchBook( dto );
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
		
		return "main";
	}
	
	@RequestMapping("/selectSearchHist")
	public String selectSearchHist( @RequestBody UserInfoDto dto ) {
		
		log.info( "selectSearchHist => {}", dto );
		
		userSearchHistService.selectUserSearchHist(dto.getUserId());
		
		return "main";
	}
	
	@RequestMapping("/selectMostSearchWord")
	public String selectMostSearchWord( @RequestBody UserInfoDto dto ) {
		
		log.info( "selectSearchHist => {}", dto );
		
		mostSearchWordService.selectMostSearchWordList();
		
		return "main";
	}
	
	
}

package com.kkb.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.db.MostSearchWord;
import com.kkb.db.UserSearchHist;
import com.kkb.dto.SearchBookDto;
import com.kkb.dto.UserInfoDto;
import com.kkb.service.MostSearchWordService;
import com.kkb.service.SearchBookService;
import com.kkb.service.UserInfoService;
import com.kkb.service.UserSearchHistService;
import com.kkb.utils.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
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

	
	@RequestMapping(value="/loginUser", method=RequestMethod.GET)
	public Map<String,String> loginUser( @ModelAttribute UserInfoDto dto ) {
		
		log.info( "loginUser => {}", dto );
		
		String resultCode = null;
		String resultMsg = null;
		
		try {
			userInfoService.checkLoginUser( dto );
			resultCode = "00";
			resultMsg = "성공";
		} catch (Exception e) {
			e.printStackTrace();
			resultCode = "99";
			resultMsg = e.getMessage();
		}
		
		Map<String,String> resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", resultCode);
		resultMap.put("resultMsg", resultMsg);
		
		return resultMap;
	}
	
	@RequestMapping(value="/registUser", method=RequestMethod.GET)
	public Map<String,String> registUser( @ModelAttribute UserInfoDto dto ) {
		
		log.info( "registUser => {}", dto );
		
		String resultCode = null;
		String resultMsg = null;
		try {
			userInfoService.registUser( dto );
			resultCode = "00";
			resultMsg = "성공";
		} catch (Exception e) {
			e.printStackTrace();
			resultCode = "99";
			resultMsg = e.getMessage();
		}
		
		Map<String,String> resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", resultCode);
		resultMap.put("resultMsg", resultMsg);
		
		return resultMap;
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public @ResponseBody Map searchBook( @ModelAttribute SearchBookDto dto ) {
		
		log.info( "searchBook => {}", dto );
		Map resultData = null;
		String resultCode = null;
		String resultMsg = null;
		try {
			
			if ( CommonUtils.isEmpty(dto.getPage()) ) {
				dto.setPage(1);
			}
			if ( CommonUtils.isEmpty(dto.getSize()) ) {
				dto.setSize(10);
			}
			resultData = searchBookService.searchBook( dto );
			resultCode = "00";
			resultMsg = "성공";
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultCode = "99";
			resultMsg = e.getMessage();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultCode = "99";
			resultMsg = e.getMessage();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultCode = "99";
			resultMsg = e.getMessage();
		}
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultCode", resultCode);
		resultMap.put("resultMsg", resultMsg);
		resultMap.put("resultData", resultData);
		
		return resultMap;
	}
	
	@RequestMapping(value="/selectSearchHist", method=RequestMethod.GET)
	public Map<String,Object> selectSearchHist( @ModelAttribute UserInfoDto dto ) {
		
		log.info( "selectSearchHist => {}", dto );
		
		List<UserSearchHist> resultList = userSearchHistService.selectUserSearchHist(dto.getUserId());
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultCode", "00");
		resultMap.put("resultMsg", "성공");
		resultMap.put("resultData", resultList);
		
		return resultMap;
	}
	
	@RequestMapping(value="/selectMostSearchWord", method=RequestMethod.GET)
	public Map<String,Object> selectMostSearchWord( @ModelAttribute UserInfoDto dto ) {
		
		log.info( "selectSearchHist => {}", dto );
		
		List<MostSearchWord> resultList = mostSearchWordService.selectMostSearchWordList();
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultCode", "00");
		resultMap.put("resultMsg", "성공");
		resultMap.put("resultData", resultList);
		
		return resultMap;
	}
	
	
}

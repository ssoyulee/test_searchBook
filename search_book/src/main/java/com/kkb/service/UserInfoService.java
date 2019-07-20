package com.kkb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kkb.dao.UserInfoDao;
import com.kkb.db.User;
import com.kkb.dto.UserInfoDto;
import com.kkb.utils.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;
	
	/**
	 * 사용자 정보 입력
	 * @return
	 * @throws Exception 
	 */
	public boolean registUser( UserInfoDto inputUser ) throws Exception {

		log.debug("registUser > {}",inputUser);
		
		// TODO : 빈값을 비교하는 로직 추가
		if ( CommonUtils.isEmpty( inputUser ) ) {
			throw new Exception("사용자 정보가 존재하지 않습니다.");
		} else if ( CommonUtils.isEmpty( inputUser.getUserId() ) ) {
			throw new Exception("사용자 ID가 존재하지 않습니다.");
		} else if ( CommonUtils.isEmpty( inputUser.getUserPw() ) ) {
			throw new Exception("사용자 패스워드가 존재하지 않습니다.");
		}
		
		saveUser(inputUser);
		
		return true;
	}
	
	/**
	 * 사용자 정보 수정
	 * @return
	 * @throws Exception 
	 */
	public boolean updateUser( String userId, Integer lastSearchSeq ) throws Exception {
		
		log.debug("updateUser > {}",userId);
		log.debug("updateUser > {}",lastSearchSeq);
		
		if ( CommonUtils.isEmpty( userId ) ) {
			throw new Exception("사용자 ID가 존재하지 않습니다.");
		}else if  ( CommonUtils.isEmpty( lastSearchSeq ) ) {
			throw new Exception("수정할 lastSearchSeq 정보가 존재하지 않습니다. ");
		}
		userInfoDao.updateLastSearchSeq(userId, lastSearchSeq);
		
		return true;
	}
	
	
	/**
	 * 사용자 정보 저장
	 * @return
	 * @throws Exception 
	 */
	public boolean saveUser( UserInfoDto inputUser ) throws Exception {
		
		log.debug("saveUser > {}",inputUser);
		
		User user = User.builder().userId( inputUser.getUserId() ).userPw( encodingPassword(inputUser.getUserPw()) ).lastSearrchSeq(0).build();
		if ( userInfoDao.save(user) == null ) {
			throw new Exception("사용자 정보 저장 에 실패하였습니다.");
		}
		return true;
	}
	
	/**
	 * 사용자 입력받은 값과 DB에 있는 값을 비교한다.
	 * @return
	 * @throws Exception 
	 */
	public boolean checkLoginUser( UserInfoDto inputUser ) throws Exception {
		
		log.debug("checkLoginUser > {}",inputUser);
		
		User user = getUserInfo( inputUser );
		if ( user == null ) {
			throw new Exception("사용자ID가 올바르지 않습니다.");
		}
		
		log.debug("getUserInfo > {}", user);
		
		boolean result = checkedPassword ( inputUser.getUserPw(), user.getUserPw() );
		if ( !result ) {
			throw new Exception("패스워드가 올바르지 않습니다.");
		}
		
		log.debug("checkedPassword > {}", result);
		
		return true;
	}

	/**
	 * 사용자 정보를 가져온다.
	 * @return
	 */
	protected User getUserInfo( String userId ) {
		
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setUserId(userId);
		
		return getUserInfo(userInfoDto);
	}
	
	/**
	 * 사용자 정보를 가져온다.
	 * @return
	 */
	private User getUserInfo( UserInfoDto inputUser ) {
		
		log.debug("getUserInfo > {}", inputUser);
		
		User user = userInfoDao.selectUserById( inputUser.getUserId() );
		return user;
	}
	
	/**
	 * 패스워드 암호화
	 * @return
	 */
	protected String encodingPassword( String password ) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(password);
	}
	
	/**
	 * 패스워드 비교
	 * @return
	 */
	protected boolean checkedPassword( String inputPassword, String savedPassword ) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(inputPassword, savedPassword);
	}
}

package com.kkb.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kkb.db.User;

@Repository
public interface UserInfoDao extends JpaRepository<User, Long>{

	@Query("SELECT u FROM User u WHERE u.userId = ?1")
	public User selectUserById(String userId);
	
	@Modifying
	@Transactional
	@Query("UPDATE User u set u.lastSearrchSeq = :lastSearchSeq where u.userId = :userId")
	public void updateLastSearchSeq(String userId, int lastSearchSeq);
}

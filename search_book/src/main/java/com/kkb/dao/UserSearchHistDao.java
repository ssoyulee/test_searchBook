package com.kkb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kkb.db.UserSearchHist;
import com.kkb.db.UserSearchPK;

@Repository
public interface UserSearchHistDao extends JpaRepository<UserSearchHist, UserSearchPK>{

}

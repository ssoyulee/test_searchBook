package com.kkb.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kkb.db.MostSearchWord;

@Repository
public interface MostSearchWordDao extends JpaRepository<MostSearchWord, Long>{

	@Query("SELECT m FROM MostSearchWord m WHERE m.searhWord = :searhWord")
	public MostSearchWord selectUserBySearhWord(String searhWord);
	
	@Modifying
	@Transactional
	@Query("UPDATE MostSearchWord m set m.searchCount = m.searchCount+1 where m.searhWord = :searhWord")
	public void updateSearchCount(String searhWord);
	
	
	public List<MostSearchWord> findTop10ByOrderBySearchCountDesc();
	
}

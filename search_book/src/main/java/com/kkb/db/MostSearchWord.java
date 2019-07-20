package com.kkb.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Getter
@Entity
public class MostSearchWord {

	@Id
	@Column(nullable = false)
	private String searhWord;
	
	@Column(nullable = false)
	private long searchCount;
	
    @Builder
    public MostSearchWord(String searhWord, long searchCount) {
        this.searhWord = searhWord;
        this.searchCount = searchCount;
    }
    
}

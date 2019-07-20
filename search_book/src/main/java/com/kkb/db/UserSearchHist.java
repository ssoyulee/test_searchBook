package com.kkb.db;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Getter
@Entity
public class UserSearchHist {
	
    @EmbeddedId
    private UserSearchPK pk;
    
    @Column(nullable = false)
    private String searchWord;
    
    @Builder
    public UserSearchHist(String userId, int seq, String searchWord) {
        this.pk = UserSearchPK.builder().userId(userId).seq(seq).build();
        this.searchWord = searchWord;
    }
    
    public int getSeq() {
    	return this.pk.getSeq();
    }
    
}

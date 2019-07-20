package com.kkb.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@ToString
@Getter
@Setter
public class UserSearchPK implements Serializable{
	
	public UserSearchPK () {}

	@Column(nullable = false)
    private String userId;
    
    @Column(nullable = false)
    private int seq; 

    @Builder
    public UserSearchPK(String userId, int seq) {
        this.userId = userId;
        this.seq = seq;
    }
    
}
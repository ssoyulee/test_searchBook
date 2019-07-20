package com.kkb.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchBookDto {

	private String userId;
	
	private String query;
	
	private int page;
	
	private int size;
}

package com.kkb.code;

public enum ApiInfo {

	KAKAO_API("https://dapi.kakao.com/v3/search/book", "65d2a9a305888cafb5ddf046e9af0559");
	
	private String url;
	private String key;
	
	private ApiInfo( String url, String key ) {
		this.url = url;
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}

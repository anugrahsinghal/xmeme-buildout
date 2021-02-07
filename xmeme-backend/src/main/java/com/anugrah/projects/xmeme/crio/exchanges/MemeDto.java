package com.anugrah.projects.xmeme.crio.exchanges;

import lombok.Data;

@Data
public class MemeDto {

	private String name;
	private String url;
	private String caption;

	protected MemeDto() {
	}

	public MemeDto(String name, String url, String caption) {
		this.name = name;
		this.url = url;
		this.caption = caption;
	}
}
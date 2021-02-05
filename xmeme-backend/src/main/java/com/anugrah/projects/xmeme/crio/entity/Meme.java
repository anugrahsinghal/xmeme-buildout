package com.anugrah.projects.xmeme.crio.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Meme implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String name;
	private String url;
	private String caption;

	protected Meme() {
	}

	public Meme(String name, String url, String caption) {
		this.name = name;
		this.url = url;
		this.caption = caption;
	}
}

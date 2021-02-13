package com.anugrah.projects.xmeme.crio.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(indexes = {@Index(columnList = "id", name = "index_on_id")})
@Data
public class Meme implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String name;
	@URL
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

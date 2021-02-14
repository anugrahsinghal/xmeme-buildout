package com.anugrah.projects.xmeme.crio.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "meme_table")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) //Provide cache strategy.
@Schema(name = "meme", description = "The Meme Entity")
public class Meme implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Schema(description = "Unique id of the meme", example = "1")
	private Long id;

	@Schema(description = "Name of the user", example = "Anugrah")
	private String name;

	@Schema(description = "Url for image of the meme", example = "http://www.google.com/funny-meme.jpg")
	private String url;

	@Schema(description = "Caption for meme", example = "A sweet meme!")
	private String caption;

	protected Meme() {
	}

	public Meme(String name, String url, String caption) {
		this.name = name;
		this.url = url;
		this.caption = caption;
	}

	@URL
	String getUrl() {
		return (url != null &&
		        !url.startsWith("http://") &&
		        !url.startsWith("https://")) ?
				"http://" + url :
				url;
	}
}

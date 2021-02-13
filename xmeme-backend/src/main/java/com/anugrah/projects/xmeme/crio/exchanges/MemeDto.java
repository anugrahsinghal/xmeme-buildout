package com.anugrah.projects.xmeme.crio.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "the object to send data to create a meme")
public class MemeDto {
	// param corresponding to Meme.name
	@NotBlank
	@Schema(description = "Name of the user", example = "Anugrah", required = true)
	@Size(max = 255)
	private String name;
	// param corresponding to Meme.url
	@Schema(description = "Url for image of the meme", required = true, example = "http://www.google.com/funny-meme.jpg")
	@NotBlank
	@Size(max = 255)
	private String url;
	// param corresponding to Meme.caption
	@Schema(description = "Caption for meme", required = true, example = "A sweet meme!")
	@NotBlank
	@Size(max = 255)
	private String caption;

}

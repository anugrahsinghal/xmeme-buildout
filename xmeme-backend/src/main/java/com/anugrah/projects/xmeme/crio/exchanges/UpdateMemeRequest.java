package com.anugrah.projects.xmeme.crio.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "the object to send data to update the meme")
public class UpdateMemeRequest {

	// param corresponding to Meme.url
	@Schema(description = "updated url for image of the meme")
	private String url;
	// param corresponding to Meme.caption
	@Schema(description = "updated caption for meme")
	private String caption;


}

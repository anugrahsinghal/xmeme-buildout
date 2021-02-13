package com.anugrah.projects.xmeme.crio.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
//@ApiModel(description = "the object to send data to update the meme")
public class UpdateMemeRequest {

	// param corresponding to Meme.url
//	@ApiModelProperty(value = "url for image of the meme")
	private String url;
	// param corresponding to Meme.caption
//	@ApiModelProperty(value = "caption for meme")
	private String caption;


}

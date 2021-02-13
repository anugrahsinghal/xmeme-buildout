package com.anugrah.projects.xmeme.crio.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "the object to send data to create a meme")
public class MemeDto {
	// param corresponding to Meme.name
	@ApiModelProperty(value = "name associated to the meme", required = true)
	private String name;
	// param corresponding to Meme.url
	@ApiModelProperty(value = "url for image of the meme", required = true)
	private String url;
	// param corresponding to Meme.caption
	@ApiModelProperty(value = "caption for meme", required = true)
	private String caption;

}

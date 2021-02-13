package com.anugrah.projects.xmeme.crio.exchanges;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Response with id allocated to the posted meme")
public class MemeCreatedResponse {
	/**
	 * The id of the Meme that was stored in the DB
	 * */
	@ApiModelProperty(value = "id allocated to the posted meme")
	private Long id;
}

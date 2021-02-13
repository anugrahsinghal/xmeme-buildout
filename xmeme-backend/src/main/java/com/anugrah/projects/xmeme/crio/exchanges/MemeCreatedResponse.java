package com.anugrah.projects.xmeme.crio.exchanges;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response with id allocated to the posted meme")
public class MemeCreatedResponse {
	/**
	 * The id of the Meme that was stored in the DB
	 * */
	@Schema(description = "unique id allocated to the posted meme")
	private Long id;
}

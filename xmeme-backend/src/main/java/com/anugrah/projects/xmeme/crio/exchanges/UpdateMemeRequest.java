package com.anugrah.projects.xmeme.crio.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemeRequest {

	private String url;
	private String caption;


}

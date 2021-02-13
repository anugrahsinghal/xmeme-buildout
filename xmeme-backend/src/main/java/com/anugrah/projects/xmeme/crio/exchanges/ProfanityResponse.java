package com.anugrah.projects.xmeme.crio.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfanityResponse {

	@JsonProperty("output")
	private Output output;

	@JsonProperty("id")
	private String id;

	public Output getOutput(){
		return output;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"output = '" + output + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
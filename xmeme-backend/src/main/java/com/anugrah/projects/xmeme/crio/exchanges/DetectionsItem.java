package com.anugrah.projects.xmeme.crio.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetectionsItem{

	@JsonProperty("bounding_box")
	private List<Integer> boundingBox;

	@JsonProperty("confidence")
	private String confidence;

	@JsonProperty("name")
	private String name;

	public List<Integer> getBoundingBox(){
		return boundingBox;
	}

	public String getConfidence(){
		return confidence;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"DetectionsItem{" + 
			"bounding_box = '" + boundingBox + '\'' + 
			",confidence = '" + confidence + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}
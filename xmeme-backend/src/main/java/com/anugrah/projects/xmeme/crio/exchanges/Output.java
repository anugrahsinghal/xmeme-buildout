package com.anugrah.projects.xmeme.crio.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Output{

	@JsonProperty("nsfw_score")
	private double nsfwScore;

	@JsonProperty("detections")
	private List<DetectionsItem> detections;

	public double getNsfwScore(){
		return nsfwScore;
	}

	public List<DetectionsItem> getDetections(){
		return detections;
	}

	@Override
 	public String toString(){
		return 
			"Output{" + 
			"nsfw_score = '" + nsfwScore + '\'' + 
			",detections = '" + detections + '\'' + 
			"}";
		}
}
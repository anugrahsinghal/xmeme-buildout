package com.anugrah.projects.xmeme.crio.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
	/*
{
    "id": "02a6ac25-cf77-4768-b2de-231f055e07b2",
    "output": {
        "detections": [
            {
                "confidence": "0.91",
                "bounding_box": [
                    46,
                    119,
                    53,
                    53
                ],
                "name": "Female Breast - Exposed"
            },
            {
                "confidence": "0.89",
                "bounding_box": [
                    119,
                    120,
                    58,
                    50
                ],
                "name": "Female Breast - Exposed"
            },
            {
                "confidence": "0.67",
                "bounding_box": [
                    96,
                    240,
                    23,
                    43
                ],
                "name": "Female Genitalia - Exposed"
            }
        ],
        "nsfw_score": 0.9997634291648865
    }
	* */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfanityResponse {

	@JsonProperty("output")
	private Output output;

	@JsonProperty("id")
	private String id;

	public Output getOutput() {
		return output;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return
				"Response{" +
				"output = '" + output + '\'' +
				",id = '" + id + '\'' +
				"}";
	}
}
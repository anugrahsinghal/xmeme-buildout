package com.anugrah.projects.xmeme.crio.validation;

import com.anugrah.projects.xmeme.crio.exceptions.MemeValidationException;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Qualifier("profanity")
public class ProfanityMemeValidationStrategyImpl implements MemeValidationStrategy {

	public static final String API_KEY = "5e16d857-f084-4521-a2da-d68a4691621f";
	public static final String API_URL = "https://api.deepai.org/api/nsfw-detector";
	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(ProfanityMemeValidationStrategyImpl.class);
	@Value("${profanity.check}")
	private String val;
	@Autowired
	private RestTemplate restTemplate;

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

	@Override
	public void validateMeme(MemeDto memeDto) {
		String url = memeDto.getUrl();
		checkProfanity(url);
	}

	private void checkProfanity(String url) {
		if (val.equalsIgnoreCase("disable")) {
			return;
		}
		boolean isNsfw = false;
		HttpHeaders headers = new HttpHeaders();
		headers.add("api-key", API_KEY);

		Map<String, String> json = new HashMap<>();
		json.put("image", url);

		HttpEntity<Map> entity = new HttpEntity<>(json, headers);

		try {
			final ResponseEntity<Map> responseEntity = restTemplate.postForEntity(API_URL, entity, Map.class);
			final Map body = responseEntity.getBody();
			final Map<String, String> output = (Map<String, String>) body.get("output");
			if (Double.parseDouble(output.get("nsfw_score")) > 0.5) {
				log.info("NSFW Image detected");
				isNsfw = true;
			}
		} catch (Exception e) {
			log.error(e);
		}

		if (!isNsfw) {
			log.info("No Profanity found");
		} else {
			throw new MemeValidationException("Meme Detected as NSFW");
		}
	}

	@Override
	public void validateMeme(UpdateMemeRequest updateMemeRequest) {
		checkProfanity(updateMemeRequest.getUrl());
	}
}

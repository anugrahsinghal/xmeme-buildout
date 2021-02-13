package com.anugrah.projects.xmeme.crio.validation;

import com.anugrah.projects.xmeme.crio.exceptions.MemeValidationException;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.ProfanityRequest;
import com.anugrah.projects.xmeme.crio.exchanges.ProfanityResponse;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Checks Image URL to not contain NSFW images using the deep.ai api
 */
@Component
@Qualifier("profanity")
public class ProfanityMemeValidationStrategyImpl implements MemeValidationStrategy {

	public static final String API_KEY = "5e16d857-f084-4521-a2da-d68a4691621f";
	public static final String API_URL = "https://api.deepai.org/api/nsfw-detector";
	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(ProfanityMemeValidationStrategyImpl.class);
	@Value("${profanity.check}")
	private String state;
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
		if (state.equalsIgnoreCase("disable")) {
			return;
		}

		boolean isNsfw = false;
		HttpHeaders headers = new HttpHeaders();
		headers.add("api-key", API_KEY);

		ProfanityRequest profanityRequest = new ProfanityRequest(url);

		HttpEntity<ProfanityRequest> entity = new HttpEntity<>(profanityRequest, headers);

		try {
			final ResponseEntity<ProfanityResponse> responseEntity = restTemplate.postForEntity(API_URL, entity, ProfanityResponse.class);
			final ProfanityResponse profanityResponse = responseEntity.getBody();
			if (profanityResponse != null && profanityResponse.getOutput().getNsfwScore() > 0.25) {
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

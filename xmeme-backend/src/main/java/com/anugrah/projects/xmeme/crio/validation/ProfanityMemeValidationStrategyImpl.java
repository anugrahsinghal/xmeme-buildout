package com.anugrah.projects.xmeme.crio.validation;

import com.anugrah.projects.xmeme.crio.exceptions.MemeValidationException;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.ProfanityResponse;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
	@Value("${spring.profiles.active:dev}")
	private String state;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void validateMeme(MemeDto memeDto) {
		String url = memeDto.getUrl();
		checkProfanity(url);
	}

	/**
	 * Checks the url from deepAi API for NSFW content
	 *
	 * @param url the image url to check for profanity
	 */
	private void checkProfanity(String url) {
		if (state.equalsIgnoreCase("dev")) {
			return;
		}

		boolean isNsfw = false;
		HttpHeaders headers = new HttpHeaders();
		headers.add("api-key", API_KEY);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("image", url);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		try {
			final ResponseEntity<ProfanityResponse> responseEntity = restTemplate.postForEntity(API_URL, request, ProfanityResponse.class);
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

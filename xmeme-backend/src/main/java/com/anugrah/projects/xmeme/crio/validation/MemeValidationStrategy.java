package com.anugrah.projects.xmeme.crio.validation;

import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import org.springframework.stereotype.Service;

/**
 * @implSpec Validate the Meme creation or update request from various
 */
@Service
public interface MemeValidationStrategy {
	void validateMeme(MemeDto memeDto);

	void validateMeme(UpdateMemeRequest updateMemeRequest);

}

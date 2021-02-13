package com.anugrah.projects.xmeme.crio.validation;

import com.anugrah.projects.xmeme.crio.exceptions.MemeValidationException;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import com.anugrah.projects.xmeme.crio.repository.MemeRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("all-unique")
public class AllValuesUniqueMemeValidationStrategyImpl implements MemeValidationStrategy {
	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(AllValuesUniqueMemeValidationStrategyImpl.class);

	@Autowired
	private MemeRepository memeRepository;

	@Override
	public void validateMeme(MemeDto memeDto) {
		final boolean captionExists = memeRepository.existsMemeByCaption(memeDto.getCaption());
		final boolean urlExists = memeRepository.existsMemeByUrl(memeDto.getUrl());
		final boolean nameExists = memeRepository.existsMemeByName(memeDto.getName());
		final boolean memeExists = captionExists || urlExists || nameExists;
		if (memeExists) {
			log.error("Meme already exists -> Caption [{}], URL [{}], Name [{}]", captionExists, urlExists, nameExists);
			throw new MemeValidationException("Meme already exists");
		}
		log.info("Meme is unique");
	}

	@Override
	public void validateMeme(UpdateMemeRequest updateMemeRequest) {
		final boolean captionExists = memeRepository.existsMemeByCaption(updateMemeRequest.getCaption());
		final boolean urlExists = memeRepository.existsMemeByUrl(updateMemeRequest.getUrl());
		final boolean memeExists = captionExists || urlExists;
		if (memeExists) {
			log.error("Meme already exists -> Caption [{}], URL [{}]", captionExists, urlExists);
			throw new MemeValidationException("Meme already exists");
		}
		log.info("Meme is unique");
	}
}

package com.anugrah.projects.xmeme.crio.service;

import com.anugrah.projects.xmeme.crio.entity.Meme;
import com.anugrah.projects.xmeme.crio.exceptions.MemeNotFoundException;
import com.anugrah.projects.xmeme.crio.exchanges.MemeCreatedResponse;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import com.anugrah.projects.xmeme.crio.repository.MemeRepository;
import com.anugrah.projects.xmeme.crio.validation.MemeValidationStrategy;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MemeStorageServiceImpl implements MemeStorageService {

	private static final Logger log = LogManager.getLogger(MemeStorageServiceImpl.class);

	@Autowired
	private MemeRepository memeRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Qualifier("combination-unique")
	@Autowired
	private MemeValidationStrategy uniqueValuesValidation;

	@Qualifier("profanity")
	@Autowired
	private MemeValidationStrategy profanityValidation;

	@Qualifier("non-empty")
	@Autowired
	private MemeValidationStrategy nonEmptyValidation;

	@Override
	public MemeCreatedResponse createMeme(MemeDto memeDto) {
		nonEmptyValidation.validateMeme(memeDto);
		uniqueValuesValidation.validateMeme(memeDto);
		profanityValidation.validateMeme(memeDto);

		final Meme meme = modelMapper.map(memeDto, Meme.class);

		final Meme savedMeme = memeRepository.save(meme);
		log.info("MEME SAVED {}", savedMeme.getId());

		return new MemeCreatedResponse(savedMeme.getId());
	}


	@Override
	public void updateMeme(final Long id, UpdateMemeRequest updateMemeRequest) {
		nonEmptyValidation.validateMeme(updateMemeRequest);
		uniqueValuesValidation.validateMeme(updateMemeRequest);
		profanityValidation.validateMeme(updateMemeRequest);

		final Optional<Meme> memeOptional = memeRepository.findById(id);
		if (memeOptional.isPresent()) {
			final Meme meme = memeOptional.get();

			Meme updatedMeme = updateMemeData(meme, updateMemeRequest);

			memeRepository.save(updatedMeme);
		} else {
			throw new MemeNotFoundException(String.format("Meme for id %s not found", id));
		}
	}

	private Meme updateMemeData(Meme meme, UpdateMemeRequest updateMemeRequest) {
		if (updateMemeRequest.getCaption() != null) {
			meme.setCaption(updateMemeRequest.getCaption());
		}
		if (updateMemeRequest.getUrl() != null) {
			meme.setUrl(updateMemeRequest.getUrl());
		}
		return meme;
	}

	@Override
	public void deleteMeme(Long id) {
		memeRepository.deleteById(id);
	}

}

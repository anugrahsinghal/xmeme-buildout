package com.anugrah.projects.xmeme.crio.service;

import static org.springframework.util.ObjectUtils.isEmpty;


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
	private static final Logger LOG = LogManager.getLogger(MemeStorageServiceImpl.class);

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
		performValidations(memeDto);
		log.info("now using model mapper");
		Meme meme;
		try {
			meme = modelMapper.map(memeDto, Meme.class);
		} catch (Exception e) {
			log.info("Exception in model mapper", e);
			log.info("Tryign using constructor");
			meme = new Meme(memeDto.getName(), memeDto.getUrl(), memeDto.getCaption());
			log.info("meme made");
		}
		log.info("after model mapper try catch block [{}]", meme);


		final Meme savedMeme = memeRepository.save(meme);
		log.info("MEME SAVED {}", savedMeme.getId());

		return new MemeCreatedResponse(savedMeme.getId());
	}


	@Override
	public void updateMeme(final Long id, UpdateMemeRequest updateMemeRequest) {
		performValidations(updateMemeRequest);

		final Optional<Meme> memeOptional = memeRepository.findById(id);
		if (memeOptional.isPresent()) {
			final Meme meme = memeOptional.get();
			LOG.info("updateMemeRequest [{}]", updateMemeRequest);
			Meme updatedMeme = updateMemeData(meme, updateMemeRequest);
			LOG.info("updatedMeme [{}]", updatedMeme);
			validateUpdatedMemeIsUnique(updatedMeme);

			memeRepository.save(updatedMeme);
		} else {
			throw new MemeNotFoundException(String.format("Meme for id %s not found", id));
		}
	}

	private void validateUpdatedMemeIsUnique(Meme updatedMeme) {
		final MemeDto memeDto = modelMapper.map(updatedMeme, MemeDto.class);
		log.info("memeDto [{}]", memeDto);
		uniqueValuesValidation.validateMeme(memeDto);
	}

	/**
	 * @param meme              entity fetched from db
	 * @param updateMemeRequest request object with new values
	 * @return updated meme entity to be persisted
	 * @implSpec checks for null or empty values before assigning them to the Entity
	 */
	private Meme updateMemeData(Meme meme, UpdateMemeRequest updateMemeRequest) {
		if (!isEmpty(updateMemeRequest.getCaption())) {
			meme.setCaption(updateMemeRequest.getCaption());
		}
		if (!isEmpty(updateMemeRequest.getUrl())) {
			meme.setUrl(updateMemeRequest.getUrl());
		}
		return meme;
	}

	@Override
	public void deleteMeme(Long id) {
		final Optional<Meme> memeOptional = memeRepository.findById(id);
		if (memeOptional.isPresent()) {
			memeRepository.deleteById(id);
		} else {
			throw new MemeNotFoundException(String.format("Meme for id %s not found", id));
		}
	}

	/**
	 * @param memeDto data to be validated against multiple conditions before we perform operations on it
	 */
	void performValidations(MemeDto memeDto) {
		nonEmptyValidation.validateMeme(memeDto);
		uniqueValuesValidation.validateMeme(memeDto);
		profanityValidation.validateMeme(memeDto);
		log.info("validation done");
	}

	//	@SneakyThrows(value = {DuplicateMemeException.class, MemeValidationException.class})

	/**
	 * @param updateMemeRequest data to be validated against multiple conditions before we perform operations on it
	 */
	void performValidations(UpdateMemeRequest updateMemeRequest) {
		nonEmptyValidation.validateMeme(updateMemeRequest);
		profanityValidation.validateMeme(updateMemeRequest);
	}


}

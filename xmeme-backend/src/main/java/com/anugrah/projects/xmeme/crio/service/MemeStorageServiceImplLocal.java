package com.anugrah.projects.xmeme.crio.service;

import com.anugrah.projects.xmeme.crio.entity.Meme;
import com.anugrah.projects.xmeme.crio.exceptions.MemeNotFoundException;
import com.anugrah.projects.xmeme.crio.exceptions.MemeUpdateException;
import com.anugrah.projects.xmeme.crio.exceptions.MemeValidationException;
import com.anugrah.projects.xmeme.crio.exchanges.MemeCreatedResponse;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import com.anugrah.projects.xmeme.crio.repository.MemeRepository;
import java.util.Optional;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class MemeStorageServiceImplLocal implements MemeStorageService {

	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(MemeStorageServiceImplLocal.class);
	@Autowired
	private MemeRepository memeRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public MemeCreatedResponse createMeme(MemeDto memeDto) {
//		validateThatMemeIsUnique(memeDto);

		final Meme meme = modelMapper.map(memeDto, Meme.class);

		final Meme savedMeme = memeRepository.save(meme);
		log.info("MEME SAVED {}", savedMeme.getId());

		return new MemeCreatedResponse(savedMeme.getId());
	}

	private void validateThatMemeIsUnique(MemeDto memeDto) {
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

	private void validateThatMemeIsUnique(Meme meme) {
		final boolean memeExists = memeRepository.exists(Example.of(meme));
		log.info("Meme already memeExists {}", memeExists);

		if (memeExists) {
			throw new MemeValidationException("Meme already exists");
		}
	}

	@Override
	public void updateMeme(Long id, UpdateMemeRequest updateMemeRequest) {
		validateUpdateRequest(updateMemeRequest);

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

	private void validateUpdateRequest(UpdateMemeRequest updateMemeRequest) {
		if (updateMemeRequest.getUrl() == null && updateMemeRequest.getCaption() == null) {
			throw new MemeUpdateException("Both url and caption cannot be null");
		}
	}

	@Override
	public void deleteMeme(Long id) {

	}
}

package com.anugrah.projects.xmeme.crio.service;

import com.anugrah.projects.xmeme.crio.entity.Meme;
import com.anugrah.projects.xmeme.crio.exceptions.MemeNotFoundException;
import com.anugrah.projects.xmeme.crio.exceptions.MemeUpdateException;
import com.anugrah.projects.xmeme.crio.exchanges.MemeCreatedResponse;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import com.anugrah.projects.xmeme.crio.repository.MemeRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemeStorageServiceImpl implements MemeStorageService {

	@Autowired
	private MemeRepository memeRepository;

	@Override
	public MemeCreatedResponse createMeme(final String name, final String url, final String caption) {
		final Meme meme = new Meme(name, url, caption);

		final Meme savedMeme = memeRepository.save(meme);

		return new MemeCreatedResponse(savedMeme.getId());
	}

	@Override
	public MemeCreatedResponse updateMeme(Long id, UpdateMemeRequest updateMemeRequest) {
		validateUpdateRequest(updateMemeRequest);
		MemeCreatedResponse memeCreatedResponse;

		final Optional<Meme> memeOptional = memeRepository.findById(id);
		if (memeOptional.isPresent()) {
			final Meme meme = memeOptional.get();

			Meme updatedMeme = updateMemeData(meme, updateMemeRequest);

			final Meme savedMeme = memeRepository.save(updatedMeme);

			memeCreatedResponse = new MemeCreatedResponse(savedMeme.getId());
		} else {
			throw new MemeNotFoundException(String.format("Meme for id %s not found", id));
		}

		return memeCreatedResponse;
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


}

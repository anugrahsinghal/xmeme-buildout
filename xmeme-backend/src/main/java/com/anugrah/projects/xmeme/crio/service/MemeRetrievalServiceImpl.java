package com.anugrah.projects.xmeme.crio.service;

import com.anugrah.projects.xmeme.crio.entity.Meme;
import com.anugrah.projects.xmeme.crio.exceptions.MemeNotFoundException;
import com.anugrah.projects.xmeme.crio.repository.MemeRepository;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemeRetrievalServiceImpl implements MemeRetrievalService {

	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(MemeRetrievalServiceImpl.class);
	@Autowired
	private MemeRepository memeRepository;

	@Override
	public List<Meme> retrieveMemes(Pageable page) {
		final Page<Meme> memes = memeRepository.findAll(page);
		log.info("Memes size = {}", memes.getContent().size());
		return memes.getContent();
	}

	@Override
	public List<Meme> retrieveMemes() {
		return memeRepository.findTop100ByOrderByIdDesc();
	}

	@Override
	@SneakyThrows(value = {MemeNotFoundException.class})
	public Meme retrieveMeme(Long id) {
		Meme meme;
		final Optional<Meme> memeOptional = memeRepository.findById(id);

		if (memeOptional.isPresent()) {
			meme = memeOptional.get();
		} else {
			throw new MemeNotFoundException(String.format("Meme for id %s not found", id));
		}

		return meme;
	}

}

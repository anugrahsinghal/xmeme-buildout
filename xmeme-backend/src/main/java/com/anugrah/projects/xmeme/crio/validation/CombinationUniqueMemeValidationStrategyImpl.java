package com.anugrah.projects.xmeme.crio.validation;

import com.anugrah.projects.xmeme.crio.entity.Meme;
import com.anugrah.projects.xmeme.crio.exceptions.MemeValidationException;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.repository.MemeRepository;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component
@Qualifier("combination-unique")
public class CombinationUniqueMemeValidationStrategyImpl implements MemeValidationStrategy {
	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(CombinationUniqueMemeValidationStrategyImpl.class);

	@Autowired
	MemeRepository memeRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void validateMeme(MemeDto memeDto) {
		Meme meme = modelMapper.map(memeDto, Meme.class);
		final boolean memeExists = memeRepository.exists(Example.of(meme));
		log.info("Meme already memeExists {}", memeExists);

		if (memeExists) {
			throw new MemeValidationException("Meme already exists");
		} else {
			log.info("Meme is Unique");
		}
	}


}

package com.anugrah.projects.xmeme.crio.service;

import com.anugrah.projects.xmeme.crio.entity.Meme;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface MemeRetrievalService {
	List<Meme> retrieveMemes(Pageable pageable);

	List<Meme> retrieveMemes();

	Meme retrieveMeme(Long id);
}

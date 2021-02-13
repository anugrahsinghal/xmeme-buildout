package com.anugrah.projects.xmeme.crio.service;

import com.anugrah.projects.xmeme.crio.entity.Meme;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface MemeRetrievalService {


	/**
	 * @param pageable - allows for pagination and sorting of meme data
	 * @return List of Meme - finds and returns memes according to the pageable param
	 */
	List<Meme> retrieveMemes(Pageable pageable);

	/**
	 * @return List of Meme - finds and returns the 100 latest memes from the DB
	 */
	List<Meme> retrieveMemes();

	/**
	 * @param id - id of the meme to be found
	 * @return meme - the meme with given id
	 */
	Meme retrieveMeme(Long id);
}

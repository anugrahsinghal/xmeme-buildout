package com.anugrah.projects.xmeme.crio.service;

import com.anugrah.projects.xmeme.crio.exchanges.MemeCreatedResponse;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import org.springframework.stereotype.Service;

@Service
public interface MemeStorageService {

	/**
	 * @param memeDto - meme data to be created
	 * @return memeCreatedResponse - response containing id of the meme that was created
	 */
	MemeCreatedResponse createMeme(MemeDto memeDto);

	/**
	 * @param id                - id of the meme to be updated
	 * @param updateMemeRequest - the object containing updated data
	 * @implSpec - can update single property or both property of meme at the same time
	 */
	void updateMeme(final Long id, final UpdateMemeRequest updateMemeRequest);

	/**
	 * @param id - id of the meme to be deleted
	 */
	void deleteMeme(final Long id);

}

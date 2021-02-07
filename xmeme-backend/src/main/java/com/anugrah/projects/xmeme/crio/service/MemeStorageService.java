package com.anugrah.projects.xmeme.crio.service;

import com.anugrah.projects.xmeme.crio.exchanges.MemeCreatedResponse;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;

public interface MemeStorageService {

	MemeCreatedResponse createMeme(MemeDto memeDto);

	void updateMeme(final Long id, final UpdateMemeRequest updateMemeRequest);


}

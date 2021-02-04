package com.anugrah.projects.xmeme.crio.service;

import com.anugrah.projects.xmeme.crio.exchanges.MemeCreatedResponse;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;

public interface MemeStorageService {

	MemeCreatedResponse createMeme(final String name, final String url, final String caption);

	MemeCreatedResponse updateMeme(final Long id, final UpdateMemeRequest updateMemeRequest);


}

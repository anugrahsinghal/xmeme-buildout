package com.anugrah.projects.xmeme.crio.validation;

import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;

public interface MemeValidationStrategy {
	void validateMeme(MemeDto memeDto);

	default void validateMeme(UpdateMemeRequest updateMemeRequest) {
		throw new UnsupportedOperationException("Not Supported");
	}
}

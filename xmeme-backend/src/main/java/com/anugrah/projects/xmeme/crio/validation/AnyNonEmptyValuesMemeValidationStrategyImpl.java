package com.anugrah.projects.xmeme.crio.validation;

import static org.springframework.util.ObjectUtils.isEmpty;


import com.anugrah.projects.xmeme.crio.exceptions.MemeValidationException;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("non-empty")
public class AnyNonEmptyValuesMemeValidationStrategyImpl implements MemeValidationStrategy {
	@Override
	public void validateMeme(MemeDto memeDto) {
		if (isEmpty(memeDto.getName()) || isEmpty(memeDto.getUrl()) || isEmpty(memeDto.getCaption())) {
			throw new MemeValidationException("DTO values cannot be empty");
		}
	}

	@Override
	public void validateMeme(UpdateMemeRequest updateMemeRequest) {
		if (isEmpty(updateMemeRequest.getCaption()) && isEmpty(updateMemeRequest.getUrl())) {
			throw new MemeValidationException("UpdateRequest both values cannot be empty");
		}
	}

}

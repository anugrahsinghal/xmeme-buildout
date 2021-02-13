package com.anugrah.projects.xmeme.crio.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;


import com.anugrah.projects.xmeme.crio.exceptions.MemeValidationException;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProfanityMemeValidationStrategyImplTest {

	@Autowired
	ProfanityMemeValidationStrategyImpl profanityMemeValidationStrategy;

	@Test
	void nsfwImagesAreDetected() {
		//given
		MemeDto memeDto = new MemeDto("something", "https://xxxpornstar.net/wp-content/uploads/2020/07/Dani-Daniels.jpg", "something");
		// when
		// then
		assertThrows(MemeValidationException.class, () -> {
			profanityMemeValidationStrategy.validateMeme(memeDto);
		});
		
	}

}
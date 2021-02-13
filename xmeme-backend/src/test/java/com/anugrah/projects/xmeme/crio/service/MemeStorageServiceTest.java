package com.anugrah.projects.xmeme.crio.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import com.anugrah.projects.xmeme.crio.exceptions.MemeValidationException;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.anugrah.projects.xmeme.crio.exchanges.UpdateMemeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD,scripts= "classpath:/clear.sql")
class MemeStorageServiceTest {

	@Autowired
	MemeStorageService memeStorageService;

	@Test
	void contextLoads() {
		assertNotNull(memeStorageService);
	}

	@Test
	void createMemeThrowsExceptionOnEmptyValues() {
		assertThrows(MemeValidationException.class,
				() -> memeStorageService.createMeme(new MemeDto("", "", "")));
	}

	@Test
	void updateMemeThrowsExceptionOnEmptyValues() {
		assertThrows(MemeValidationException.class,
				() -> memeStorageService.updateMeme(0L, new UpdateMemeRequest()));
	}

	@Test
	void createDuplicateMemeGivesError() {
		MemeDto memeDto = new MemeDto("Anugrah", "https://i.imgflip.com/265no1.jpg", "caption");
		memeStorageService.createMeme(memeDto);
		MemeDto copyOfMemeDto = new MemeDto("Anugrah", "https://i.imgflip.com/265no1.jpg", "caption");
		assertThrows(MemeValidationException.class,
				() -> {
					memeStorageService.createMeme(copyOfMemeDto);
				});
	}

	@Test
	void createMemeWithDuplicateValueInSomeFieldsIsOK() {
		MemeDto memeDto = new MemeDto("Anugrah", "https://i.imgflip.com/265no1.jpg", "caption");
		memeStorageService.createMeme(memeDto);
		MemeDto similarToMemeDto = new MemeDto("Anugrah", "https://i.imgflip.com/265no1.jpg", "caption2");
		assertDoesNotThrow(
				() -> {
					memeStorageService.createMeme(similarToMemeDto);
				});
	}

}
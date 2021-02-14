package com.anugrah.projects.xmeme.crio.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;


import com.anugrah.projects.xmeme.crio.entity.Meme;
import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/clear.sql")
//@SpringBootTest(properties = {
//		"spring.datasource.driverClassName=org.postgresql.Driver",
//		"spring.jpa.database=POSTGRESQL",
//		"spring.datasource.platform=postgres",
//		"spring.datasource.url=jdbc:postgresql://localhost:5432/postgres",
//		"spring.datasource.username=postgres",
//		"spring.datasource.password=postgres",
//		"spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect",
//		"spring.jpa.generate-ddl=true",
//		"spring.jpa.hibernate.ddl-auto=update",
//		"debug=false"
//
//})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemeStorageServiceTestWithPostgres {

	private static long when100RecordsOnlyTimeToFetchThemTotal = 0;
	private static long when100RecordsOnlyTimeToFetchLast100 = 0;
	@Autowired
	MemeStorageService memeStorageService;
	@Autowired
	MemeRetrievalService memeRetrievalService;

	@AfterAll
	static void diff() {
		System.out.println("when100RecordsOnlyTimeToFetchThemTotal = " + when100RecordsOnlyTimeToFetchThemTotal);
		System.out.println("when100RecordsOnlyTimeToFetchLast100 = " + when100RecordsOnlyTimeToFetchLast100);
		System.out.println("10000 - 100 " + ((double) (when100RecordsOnlyTimeToFetchLast100 - when100RecordsOnlyTimeToFetchThemTotal)) / 1000); // HATAO
		System.out.println("100 - 10000 " + ((double) (when100RecordsOnlyTimeToFetchThemTotal - when100RecordsOnlyTimeToFetchLast100)) / 1000); // HATAO
	}

	@Test
	void contextLoads() {
		assertNotNull(memeStorageService);
	}

	@RepeatedTest(5)
	void make100RecordsAndGetFetchTime() {
		for (int i = 0; i < 100; i++) {
			memeStorageService.createMeme(new MemeDto("name" + i, "http://something.com" + i, "caption" + i));
		}
		long t1 = System.currentTimeMillis(); // HATAO
		for (int i = 0; i < 10; i++) {
			final List<Meme> memes = memeRetrievalService.retrieveMemes();
			System.out.println(memes.size());
			System.err.println(memes);
		}
		long t2 = System.currentTimeMillis();
		System.out.println("make100RecordsAndGetFetchTime - Time Taken in seconds " + ((double) (t2 - t1)) / 1000); // HATAO
		when100RecordsOnlyTimeToFetchThemTotal = when100RecordsOnlyTimeToFetchThemTotal + (t2 - t1);
	}

	@RepeatedTest(5)
	void make10000RecordsAndGetFetchTime() {
		for (int i = 0; i < 10000; i++) {
			memeStorageService.createMeme(new MemeDto("name" + i, "http://something.com" + i, "caption" + i));
		}
		long t1 = System.currentTimeMillis(); // HATAO
		for (int i = 0; i < 10; i++) {
			final List<Meme> memes = memeRetrievalService.retrieveMemes();
			System.out.println(memes.size());
			System.err.println(memes);
		}
		long t2 = System.currentTimeMillis();
		System.out.println("make10000RecordsAndGetFetchTime - Time Taken in seconds " + ((double) (t2 - t1)) / 1000); // HATAO
		when100RecordsOnlyTimeToFetchLast100 = when100RecordsOnlyTimeToFetchLast100 + (t2 - t1);
	}

	@Test
	void testCACHE() {
		int i = 999999;
		memeStorageService.createMeme(new MemeDto("name" + i, "http://something.com" + i, "caption" + i));
		memeRetrievalService.retrieveMemes();
		memeRetrievalService.retrieveMemes();
	}

}
package com.anugrah.projects.xmeme.crio.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/clear.sql")
class MemesControllerTest {

	private static long when100RecordsOnlyTimeToFetchThemTotal = 0;
	private static long when10000RecordsOnlyTimeToFetchLast100 = 0;
	@Autowired
	MockMvc mockMvc;

	@AfterAll
	static void diff() {
		System.out.println("when100RecordsOnlyTimeToFetchThemTotal = " + when100RecordsOnlyTimeToFetchThemTotal);
		System.out.println("when10000RecordsOnlyTimeToFetchLast100 = " + when10000RecordsOnlyTimeToFetchLast100);
		System.out.println("10000 - 100 " + ((double) (when10000RecordsOnlyTimeToFetchLast100 - when100RecordsOnlyTimeToFetchThemTotal)) / 1000); // HATAO
		System.out.println("100 - 10000 " + ((double) (when100RecordsOnlyTimeToFetchThemTotal - when10000RecordsOnlyTimeToFetchLast100)) / 1000); // HATAO
		final boolean isFastEnough = when100RecordsOnlyTimeToFetchThemTotal * 1.2 <= when10000RecordsOnlyTimeToFetchLast100;
		System.out.println("is10k within limits " + isFastEnough);
	}

	@Test
	void emptyData() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/memes").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@RepeatedTest(1)
	void make100Records() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < 101; i++) {
			final MemeDto memeDto = new MemeDto("name" + i, "http://something.com" + i, "caption" + i);
			mockMvc.perform(MockMvcRequestBuilders.post("/memes")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(memeDto)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		}
		long t1 = System.currentTimeMillis(); // HATAO
		for (int i = 0; i < 10; i++) {
			mockMvc.perform(MockMvcRequestBuilders.get("/memes").contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		}
		long t2 = System.currentTimeMillis();
		System.out.println("make100RecordsAndGetFetchTime - Time Taken in seconds " + ((double) (t2 - t1)) / 1000); // HATAO
		when100RecordsOnlyTimeToFetchThemTotal = when100RecordsOnlyTimeToFetchThemTotal + (t2 - t1);
	}

	@RepeatedTest(1)
	void make10000Records() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < 10001; i++) {
			final MemeDto memeDto = new MemeDto("name" + i, "http://something.com" + i, "caption" + i);
			mockMvc.perform(MockMvcRequestBuilders.post("/memes")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(memeDto)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		}
		long t1 = System.currentTimeMillis(); // HATAO
		for (int i = 0; i < 10; i++) {
			mockMvc.perform(MockMvcRequestBuilders.get("/memes").contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		}
		long t2 = System.currentTimeMillis();
		System.out.println("make10000RecordsAndGetFetchTime - Time Taken in seconds " + ((double) (t2 - t1)) / 1000); // HATAO
		when10000RecordsOnlyTimeToFetchLast100 = when10000RecordsOnlyTimeToFetchLast100 + (t2 - t1);
	}


}
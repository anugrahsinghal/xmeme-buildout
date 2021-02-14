package com.anugrah.projects.xmeme.crio.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import com.anugrah.projects.xmeme.crio.exchanges.MemeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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

	@Autowired
	MockMvc mockMvc;

	@Test
	void emptyData() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/memes").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	void make100RecordsAndFetchIsok() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < 101; i++) {
			final MemeDto memeDto = new MemeDto("name" + i, "http://something.com" + i, "caption" + i);
			mockMvc.perform(MockMvcRequestBuilders.post("/memes")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(memeDto)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		}

		mockMvc.perform(MockMvcRequestBuilders.get("/memes").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

	}


	@Test
	void make10000RecordsAndFetchIsOk() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < 10001; i++) {
			final MemeDto memeDto = new MemeDto("name" + i, "http://something.com" + i, "caption" + i);
			mockMvc.perform(MockMvcRequestBuilders.post("/memes")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(memeDto)))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		}

		mockMvc.perform(MockMvcRequestBuilders.get("/memes").contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


	}


}
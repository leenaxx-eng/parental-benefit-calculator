package com.helmes.parental_benefit_calculator.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest 
@AutoConfigureMockMvc
class BenefitControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldCalculateBenefitScheduleThroughAPI() throws Exception {
		String requestBody = "{ \"grossSalary\": 4000, \"birthDate\": \"2026-01-01\" }";
		mockMvc.perform(post("/api/benefits").contentType(MediaType.APPLICATION_JSON).content(requestBody))
      .andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.monthlyPayments").exists())
			.andExpect(jsonPath("$.monthlyPayments.length()", is(12)));
	}

  @Test
	void shouldRetrieveBenefitScheduleThroughAPI() throws Exception {
    String requestBody = "{ \"grossSalary\": 4000, \"birthDate\": \"2026-01-01\" }";
    MvcResult result = mockMvc.perform(post("/api/benefits").contentType(MediaType.APPLICATION_JSON).content(requestBody))
      .andExpect(status().isCreated())
      .andReturn();

    String responseBody = result.getResponse().getContentAsString();
    Number id = JsonPath.read(responseBody, "$.id");
		
    mockMvc.perform(get("/api/benefits/" + id))
      .andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.monthlyPayments").exists())
			.andExpect(jsonPath("$.monthlyPayments.length()", is(12)));
	}
}
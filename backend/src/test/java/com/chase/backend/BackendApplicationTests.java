package com.chase.backend;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chase.backend.repository.RecipeRepository;

@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RecipeRepository recipeRepository;

	@BeforeEach
	public void deleteAllBeforeTests() throws Exception {
		recipeRepository.deleteAll();
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {

		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.recipes").exists());
	}

	@Test
	public void shouldCreateEntity() throws Exception {

		mockMvc.perform(post("/recipes").content(
				"{\"name\": \"Hibachi Chicken with Fried Rice and Vegetables\", \"category\": \"Japanese\"}"))
				.andExpect(
						status().isCreated())
				.andExpect(
						header().string("Location", containsString("recipes/")));
	}

	@Test
	public void shouldRetrieveEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/recipes").content(
				"{\"name\": \"Hibachi Chicken with Fried Rice and Vegetables\", \"category\": \"Japanese\"}"))
				.andExpect(
						status().isCreated())
				.andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				jsonPath("$.name").value("Hibachi Chicken with Fried Rice and Vegetables")).andExpect(
						jsonPath("$.category").value("Japanese"));
	}

	@Test
	public void shouldQueryEntity() throws Exception {

		mockMvc.perform(post("/recipes").content(
				"{\"name\": \"Hibachi Chicken with Fried Rice and Vegetables\", \"category\": \"Japanese\"}"))
				.andExpect(
						status().isCreated());

		mockMvc.perform(
				get("/recipes/search/findRecipeByName?name={name}", "Hibachi Chicken with Fried Rice and Vegetables"))
				.andExpect(
						status().isOk())
				.andExpect(
						jsonPath("$.category").value(
								"Japanese"));
	}

	@Test
	public void shouldUpdateEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/recipes").content(
				"{\"name\": \"Hibachi Chicken with Fried Rice and Vegetables\", \"category\": \"Japanese\"}"))
				.andExpect(
						status().isCreated())
				.andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(put(location).content(
				"{\"name\": \"Pad Thai\", \"category\": \"Thai\"}")).andExpect(
						status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				jsonPath("$.name").value("Pad Thai")).andExpect(
						jsonPath("$.category").value("Thai"));
	}

	@Test
	public void shouldPartiallyUpdateEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/recipes").content(
				"{\"name\": \"Hibachi Chicken with Fried Rice and Vegetables\", \"category\": \"Japanese\"}"))
				.andExpect(
						status().isCreated())
				.andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(
				patch(location).content("{\"name\": \"Hibachi Chicken\"}")).andExpect(
						status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				jsonPath("$.name").value("Hibachi Chicken")).andExpect(
						jsonPath("$.category").value("Japanese"));
	}

	@Test
	public void shouldDeleteEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/recipes").content(
				"{\"name\": \"Hibachi Chicken with Fried Rice and Vegetables\", \"category\": \"Japanese\"}"))
				.andExpect(
						status().isCreated())
				.andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(delete(location)).andExpect(status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isNotFound());
	}
}
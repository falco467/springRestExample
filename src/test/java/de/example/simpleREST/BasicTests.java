package de.example.simpleREST;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import de.example.simpleREST.helpers.WebEnvironmentTests;

public class BasicTests extends WebEnvironmentTests {

	@Test
	void contextLoads() {
	}

	@Test
	void restProfilePresent() {
		assertEquals(HttpStatus.OK, 
			restTemplate.getForEntity(getLocalURL("/profile/"), String.class).getStatusCode());
	}

	@Test
	void swaggerUiPresent() {
		assertEquals(HttpStatus.OK,
			restTemplate.getForEntity(getLocalURL("/swagger-ui/"), String.class).getStatusCode());
	}
}

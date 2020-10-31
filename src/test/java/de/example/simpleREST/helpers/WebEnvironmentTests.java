package de.example.simpleREST.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebEnvironmentTests {

	@LocalServerPort
	private int port;

	@Autowired
	protected TestRestTemplate restTemplate;

	/**
	 * @param path the path with a leading slash
	 * @return The absolute url for the path
	 */
	protected String getLocalURL(String path) {
		return String.format("http://localhost:%d%s", port, path);
	}
}

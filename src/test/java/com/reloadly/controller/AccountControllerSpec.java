package com.reloadly.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.reloadly.enums.NotificationPreference;
import com.reloadly.exposition.FindAccountResponse;
import com.reloadly.exposition.SaveAccountRequest;
import com.reloadly.exposition.SaveAccountResponse;
import com.reloadly.exposition.UpdateAccountRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("An account")
public class AccountControllerSpec {
	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@BeforeEach
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/accounts");
	}

	@Nested
    @DisplayName("is trying to be added with correct data")
	class AddAccountCorrectData {
		@Test
		public void save() throws Exception {
			SaveAccountRequest request = new SaveAccountRequest();
			request.setName("pepe" + System.currentTimeMillis());
			request.setPassword("pepepepe");
			request.setEmail("pepe@pepe.com");
			request.setPhone("+1 (212) 555-3456");
			request.setNotificationPreference(NotificationPreference.MAIL);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
	
			HttpEntity<SaveAccountRequest> requestEntity = new HttpEntity<>(request, headers);
	
			ResponseEntity<SaveAccountResponse> responseEntity = template.postForEntity(base.toURI(), requestEntity, SaveAccountResponse.class);
			
			assertEquals(200, responseEntity.getStatusCodeValue());
			assertNotNull(responseEntity.getBody().getId());
		}
	}
	
	@Nested
    @DisplayName("is trying to be added with incorrect data")
	class AddAccountIncorrectData {
		@Test
		public void saveWithoutName() throws Exception {
			SaveAccountRequest request = new SaveAccountRequest();
			//request.setName("pepe" + System.currentTimeMillis());
			request.setPassword("pepepepe");
			request.setEmail("pepe@pepe.com");
			request.setPhone("+1 (212) 555-3456");
			request.setNotificationPreference(NotificationPreference.MAIL);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
	
			HttpEntity<SaveAccountRequest> requestEntity = new HttpEntity<>(request, headers);
	
			ResponseEntity<SaveAccountResponse> responseEntity = template.postForEntity(base.toURI(), requestEntity, SaveAccountResponse.class);
			
			assertEquals(400, responseEntity.getStatusCodeValue());
		}
		
		@Test
		public void saveWithInvalidEmail() throws Exception {
			SaveAccountRequest request = new SaveAccountRequest();
			request.setName("pepe" + System.currentTimeMillis());
			request.setPassword("pepepepe");
			request.setEmail("pepe@");
			request.setPhone("+1 (212) 555-3456");
			request.setNotificationPreference(NotificationPreference.MAIL);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
	
			HttpEntity<SaveAccountRequest> requestEntity = new HttpEntity<>(request, headers);
	
			ResponseEntity<SaveAccountResponse> responseEntity = template.postForEntity(base.toURI(), requestEntity, SaveAccountResponse.class);
			
			assertEquals(400, responseEntity.getStatusCodeValue());
		}
	}
	
	@Nested
    @DisplayName("is trying to be updated with correct data")
	class UpdateAccountCorrectData {
		@Test
		public void update() throws Exception {
			UpdateAccountRequest request = new UpdateAccountRequest();
			request.setEmail("papa@pepe.com");
			request.setPhone("+1 (212) 999-3456");
			request.setNotificationPreference(NotificationPreference.MAIL);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
	
			HttpEntity<UpdateAccountRequest> requestEntity = new HttpEntity<>(request, headers);
			
			ResponseEntity<?> responseEntity = template.exchange((new URL(base.toURI().toString() + "/36")).toURI(), HttpMethod.PUT, requestEntity, Void.class);
			
			assertEquals(200, responseEntity.getStatusCodeValue());
		}
	}
	
	@Nested
    @DisplayName("is trying to be updated with incorrect data")
	class UpdateAccountIncorrectData {
		@Test
		public void updateIncorrectEmail() throws Exception {
			UpdateAccountRequest request = new UpdateAccountRequest();
			request.setEmail("papa@");
			request.setPhone("+1 (333) 999-3456");
			request.setNotificationPreference(NotificationPreference.MAIL);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
	
			HttpEntity<UpdateAccountRequest> requestEntity = new HttpEntity<>(request, headers);
			
			ResponseEntity<?> responseEntity = template.exchange((new URL(base.toURI().toString() + "/36")).toURI(), HttpMethod.PUT, requestEntity, Void.class);
			
			assertEquals(400, responseEntity.getStatusCodeValue());
		}
	}
	
	@Nested
    @DisplayName("is trying to be found with existent id")
	class FindAccountExistentId {
		@Test
		public void findByIdExistent() throws Exception {
			ResponseEntity<FindAccountResponse> responseEntity = template.getForEntity((new URL(base.toURI().toString() + "/1")).toURI(), FindAccountResponse.class);
			
			System.out.println(responseEntity.getBody());
			
			assertEquals(200, responseEntity.getStatusCodeValue());
		}
	}
	
	@Nested
    @DisplayName("is trying to be found with inexistent id")
	class FindAccountInexistentId {
		@Test
		public void findByIdInexistent() throws Exception {
			ResponseEntity<FindAccountResponse> responseEntity = template.getForEntity((new URL(base.toURI().toString() + "/0")).toURI(), FindAccountResponse.class);
			
			System.out.println(responseEntity.getBody());
			
			assertEquals(404, responseEntity.getStatusCodeValue());
		}
	}
}
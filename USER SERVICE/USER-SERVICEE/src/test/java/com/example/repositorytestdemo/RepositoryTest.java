package com.example.repositorytestdemo;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.example.api.UserAPI;
import com.example.entity.User;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserAPI.class)
public class RepositoryTest {
@Autowired
private MockMvc mockMvc;
@MockBean
private UserService ser;
   @Test
	public void testregisterUser() throws JsonProcessingException, Exception {
		User user=new User();
        user.setEmail("jaan@gmail.com");
        user.setName("kali");
        user.setPassword("pass");
        user.setRole("admin");
       
        
       doNothing().when(ser).register(user);
       doThrow(new Exception("")).when(ser).register(user);
        mockMvc.perform(post("").content(new ObjectMapper().writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isAccepted())
        .andExpect(jsonPath("$.message", is("User registered")))
		.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string(""));
		
	}
	@Test
	public void deleteUser() throws Exception {
		doNothing().when(ser).deleteUser(1L);
		mockMvc.perform(delete("").header("version", "v1")).andExpect(status().isOk()).andExpect(jsonPath("$.message", is("deleted")))
	.andExpect(header().string("version", "v1"));
		
		}
	@Test
	public void testregisteruserexception() throws Exception {
		User user=new User();
		//id is automatically generated
		user.setEmail("lacs4567@gmail.com");
		user.setName("laks");
		user.setPassword("12355");
		user.setRole("USER");
		
		doThrow(new Exception("user data is not added")).when(ser).register(user);
		mockMvc.perform(post("").content(new ObjectMapper().writeValueAsString(user)).content(MediaType.APPLICATION_JSON_VALUE)
				).andExpect(status().isNoContent());
	}
	
}

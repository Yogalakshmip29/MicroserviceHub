package com.example.servicestestdemo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;

@ExtendWith(SpringExtension.class)
class UserServiceeApplicationTests {
@Mock
private UserRepository rep;

@InjectMocks
private UserService ser;


	public void testGetUser() throws Exception {
		User user=new User();
		user.setId(1L);
		user.setName("yoga");
		user.setPassword("yoga");
		user.setEmail("lacs678@gmail.com");
		user.setRole("ADMIN");
		Mockito.when(rep.findById(1L)).thenReturn(Optional.of(user));
		Assertions.assertEquals(user, ser.getUser(1L));
	}
	
	
	public void register() throws Exception {
		User user=new User();
		user.setName("yoga");
		user.setPassword("yoga");
		user.setEmail("lacs678@gmail.com");
		user.setRole("ADMIN");
		
		doNothing().when(rep.save(any(User.class)));
		verify(ser,times(1)).register(user);
	}
}

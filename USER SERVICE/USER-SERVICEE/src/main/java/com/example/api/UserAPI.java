package com.example.api;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.entity.orderentity.Order;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.security.FetchDetails;
import com.example.security.Jwt;
import com.example.security.UserRequest;
import com.example.security.VerificationDetails;
import com.example.service.UserService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

@RestController

@RequestMapping(value="/login",headers="API.VERSION=1")
public class UserAPI {
@Autowired
private UserService ser;
@Autowired
private UserRepository rep;
@Autowired
private VerificationDetails df;

@Autowired
private Jwt jwt;
String ticket=null;
private RestTemplate restTemplate=new RestTemplate();
@Autowired
private UserDetails userdet;
	@Autowired
	private AuthenticationManager manager;
	
	@PostMapping(value="/register",consumes=MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> register(@RequestBody User user){
		try {
		ser.register(user);
		return new ResponseEntity<>("User registered",HttpStatus.ACCEPTED);
	
	}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	@PostMapping(value="/authenticate")
	public String authenticate(@RequestBody UserRequest userRequest,@RequestParam(value="v1",defaultValue="v2")String version){
		
		manager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUserName(),userRequest.getPassword()));
		this.userdet=df.loadUserByUsername(userRequest.getUserName());
        
		return jwt.generateToken(userdet.getUsername());
	
	}
	
	@GetMapping(value="/get/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id,@RequestHeader("Authorization")String token) throws Exception{
		if(token!=null && token.startsWith("Bearer ")) {
		ticket=	token.substring(7);
		}
		
		if(jwt.validateToken(ticket)) {
			
		User user=ser.getUser(id);
		return new ResponseEntity<>(user,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("token is invalid",HttpStatus.UNAUTHORIZED);
		}
		
		
	}
	
	public ResponseEntity<?> orderService(){
		String url="http://localhost:1234/orderService/post";
		Long userId=ser.getUserId(userdet.getUsername(), userdet.getPassword());
		Order order=new Order();
		
		order.setUserId(userId);
		order.setOrderDate(new Date());
		order.setStatus("Ordering");
		order.setTotalAmount(25000D);

		HttpHeaders head=new HttpHeaders();
        head.set("Authorization", "Bearer " + ticket);
		head.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Order> entity=new HttpEntity<>(order,head);
		ResponseEntity<Order> retur=restTemplate.exchange(url, HttpMethod.POST,entity,Order.class);
		if(retur.getStatusCode().is2xxSuccessful()) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id,@RequestHeader(value="version",defaultValue="vet")String version)
	{
		if(ser.rateLimiter("/login/delete/{id}"))
		ser.deleteUser(id);
		return new ResponseEntity<>("deleted",HttpStatus.OK);
	}
	
	@GetMapping(value="/getPage")
	public ResponseEntity<Page<User>> getAllinPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize,String name){
		Page<User> user=ser.getAllUsers(pageNo, pageSize, name);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
}

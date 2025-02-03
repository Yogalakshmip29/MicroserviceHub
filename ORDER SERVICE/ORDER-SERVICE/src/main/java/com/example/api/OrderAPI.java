package com.example.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.entity.Order;
import com.example.exception.InfyBankException;
import com.example.paymentEntity.Payment;
import com.example.service.OrderServiceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping(value="/orderService",params="API.VERSION=1")
public class OrderAPI {
@Autowired
private OrderServiceImpl impl;
@Autowired
private RestTemplate rest;
	@PostMapping(value="/post/v1")
	public ResponseEntity<?> createOrder(@RequestBody Order order){
		UUID Orderid=impl.createOrder(order);
		String url="http://localhost:2345/api/post";
		Payment payment=new Payment();
		payment.setOrderId(Orderid);
		payment.setPaymentMethod("online");
		payment.setPaymentStatus("paid");
		ResponseEntity<Payment> pay=rest.postForEntity(url,payment,Payment.class);
		if(pay.getStatusCode().is2xxSuccessful()) {
		return ResponseEntity.status(HttpStatus.CREATED).body("order created successfully");}
		else
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="get/{id}")
	@Retry(name="OrderFailiure",fallbackMethod="fm")
	public ResponseEntity<?> getOrder(@PathVariable Long id) throws InfyBankException{
		Order order=impl.getOrder(id);	
	Link self=	WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderAPI.class).getOrder(id)).withSelfRel();
	Link create=	WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderAPI.class).createOrder(null)).withRel("create a order");
	EntityModel model=	EntityModel.of(self,create);
		return new ResponseEntity<>(model,HttpStatus.OK);
		
		
	}
	
	public ResponseEntity<String> fm(){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unresponsive");
	}
	
	
}

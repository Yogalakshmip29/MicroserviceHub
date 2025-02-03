package com.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Payment;
import com.example.service.PaymentService;

@RestController
@RequestMapping(value="/api",produces="application/com.example+json")
public class PaymentAPI {
	@Autowired
	private PaymentService ser;

    @PostMapping(value="/post",consumes="application/com.example+json")
	public ResponseEntity<Payment> paymentProcess(@RequestBody Payment payment){
  
		Payment paym=ser.paymentProcess(payment);

		return new ResponseEntity<>(paym,HttpStatus.CREATED);
	}
    
}

package com.example.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.entity.Order;
import com.example.exception.InfyBankException;
import com.example.repository.OrderRep;

@Service(value="ser")

public class OrderServiceImpl {
@Autowired
private OrderRep rep;
	public UUID createOrder(Order order) {
		
		rep.save(order);
		return UUID.randomUUID();
		
	}
	
	public Order getOrder(Long id) throws InfyBankException {
		Order order=rep.findById(id).orElseThrow(()->new InfyBankException("id is not available"));
		return order;
	}
	
}

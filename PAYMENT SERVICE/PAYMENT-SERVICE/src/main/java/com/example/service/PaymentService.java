package com.example.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.PaymentDTO;
import com.example.entity.Payment;
import com.example.repository.PaymentRep;

@Service(value="ser")
public class PaymentService {
@Autowired
private PaymentRep rep;
@Autowired
private ModelMapper modelMapper=new ModelMapper();

	public Payment paymentProcess(Payment payment) {
		payment.setId(UUID.randomUUID().toString());
		Payment pay=rep.save(payment);
		return pay;
	}
	public PaymentDTO getPaymentdetails(String id) throws Exception {
		Payment payment=rep.findById(id).orElseThrow(()->new Exception(""));
		return modelMapper.map(payment, PaymentDTO.class);
	}
	
}

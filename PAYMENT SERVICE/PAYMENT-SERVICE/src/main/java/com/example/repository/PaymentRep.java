package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Payment;
@Repository(value="rep")
public interface PaymentRep extends CrudRepository<Payment,String> {

}

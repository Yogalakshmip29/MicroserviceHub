package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Order;

public interface OrderRep extends CrudRepository<Order,Long>{

}

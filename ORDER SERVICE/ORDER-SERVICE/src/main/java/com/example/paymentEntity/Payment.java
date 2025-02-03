package com.example.paymentEntity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {
	@Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    
    private UUID orderId;
    private Double amount;
    private String paymentMethod;
    private String paymentStatus;

    // Constructors
    public Payment() {}

    public Payment(UUID orderId, Double amount, String paymentMethod, String paymentStatus) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String string) {
        this.id = string;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderid2) {
        this.orderId = orderid2;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}

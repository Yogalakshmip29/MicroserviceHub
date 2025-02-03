package com.example.exception;

public class InfyBankException extends Exception{
 
	private static final Long SerialVersionUID=1L;
	
	public InfyBankException(String message) {
		super(message);
	}
}

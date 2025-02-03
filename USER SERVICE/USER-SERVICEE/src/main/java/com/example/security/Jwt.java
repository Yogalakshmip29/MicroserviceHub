package com.example.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Jwt {
	Boolean b=null;
	private String secret_key="secret";
public String generateToken(String userName) {
	return Jwts.builder().signWith(SignatureAlgorithm.HS512,secret_key).setSubject(userName)
			.setExpiration(new Date()).setIssuedAt(new Date()).compact();
}

public String getUsername(String token) {
	return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody().getSubject();
}

public Date getExpiration(String token) {
	return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody().getExpiration();
	
}

public boolean validateToken(String token) {
	
	 Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token);
	 if(isTokenExpired(token)==false) {
		 b=true;
	 }
		
	 return b;
			
}
public boolean isTokenExpired(String token) {
	return getExpiration(token).before(new Date());
}
}

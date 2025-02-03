package com.example.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class Jwt {
	Boolean b=null;
	private String secret_key="secret";


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

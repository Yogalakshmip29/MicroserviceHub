package com.example.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Header;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class Filere extends OncePerRequestFilter {
@Autowired
private Jwt j;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String token=generateToken(request);
		if(token!=null)
		{
			if(j.validateToken(token)==true)
			{
				String name=j.getUsername(token);
				UsernamePasswordAuthenticationToken un=new UsernamePasswordAuthenticationToken(name,null,null);
				SecurityContextHolder.getContext().setAuthentication(un);
				filterChain.doFilter(request, response);
			}
			
			
		}
		
		
	}
   public String generateToken(HttpServletRequest request) {
	   
	   String head=request.getHeader("beanName");
	   if(head!=null&&head.startsWith("Bearer ")) {
		   String token=head.substring(7);
		  return token;
	   }
	   else {
		   return null;
	   }
	   
   }
}

package com.example.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.dto.Userdto;
import com.example.entity.User;
//import com.example.exception.InfyBankException;
import com.example.repository.UserRepository;

public class VerificationDetails implements UserDetailsService{
@Autowired
private UserRepository rep;
@Autowired
private PasswordEncoder passwordEncoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		

	Optional<User> optional=rep.findByUserName(username);
	User user=optional.orElseThrow(()->new UsernameNotFoundException("user is not found"));
	Userdto dt=new Userdto();
	dt.setId(user.getId());
	dt.setName(user.getName());
	dt.setEmail(user.getEmail());
	dt.setPassword(passwordEncoder.encode(user.getPassword()));
	return new FetchDetails(dt);
	}

}

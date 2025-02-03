package com.example.service;



import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.example.entity.User;
import com.example.repository.UserRepository;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@CacheConfig(cacheNames="cache1")
@Service(value="user")
public class UserService {
@Autowired
private UserRepository rep;
private String secret_key="secret";
private Bucket bucket = null;
@CachePut(key="#User.id")
public void register(User user) throws Exception {
		
		User us=rep.save(user);
		if(us==null) {
		throw new Exception("user data is not added");
		}
		
	}

@Cacheable(value="cache1",key="#id")	
public User getUser(Long id) throws Exception {
	User user=rep.findById(id).orElseThrow(()->new Exception(""));
	return user;
}
@Cacheable
public Long getUserId(String name,String password) {
	return rep.findByUserNameAndPassword(name,password);
}
@CacheEvict(key="#id")
@Scheduled(fixedRate=2000)
public void deleteUser(Long id) {
	rep.deleteById(id);
}

public Page<User> getAllUsers(int pageNo,int pageSize,String name) {
	Sort sort=Sort.by(name).ascending();
	Pageable pageable=PageRequest.of(pageNo, pageSize,sort);
	Page<User> user=rep.findAll(pageable);
	return user;
}

public boolean rateLimiter(String endpoint) {
	if(Pattern.matches("",endpoint)) {
	Bandwidth bw=Bandwidth.classic(100, Refill.intervally(100, Duration.ofMinutes(80)));
	this.bucket=Bucket4j.builder().addLimit(bw).build();
	return bucket.tryConsume(1) ;
	}
	else {
		
		return false;
	}
	
}
	}

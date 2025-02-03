package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.User;
@Repository(value="REP")
public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findByUserName(String username);
@Query("select u.id from User u where u.name=:name and u.password:password")
	Long findByUserNameAndPassword(@Param("name")String username, @Param("password")String password);

}

package com.shivu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivu.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmailId(String emailId);

	boolean existsByEmailId(String adminEmailId);

	boolean existsByPassword(String adminPassword);

	User findByRole(String role);

	

}

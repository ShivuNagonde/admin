package com.shivu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivu.model.EndUser;

public interface EndUserRepository extends JpaRepository<EndUser, Integer> {

	EndUser findByEmailId(String emailId);

	boolean existsByEmailId(String emailId);

	boolean existsByPassword(String password);

}

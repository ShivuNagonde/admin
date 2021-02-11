package com.shivu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivu.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}

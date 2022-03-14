package com.hedza06.springcloud.product.repositories;

import com.hedza06.springcloud.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> { }

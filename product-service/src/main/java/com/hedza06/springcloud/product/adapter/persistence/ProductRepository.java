package com.hedza06.springcloud.product.adapter.persistence;

import com.hedza06.springcloud.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>
{

}

package com.hedza06.springcloud.user.repositories;

import com.hedza06.springcloud.user.entities.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, Integer> { }

package com.hedza06.springcloud.user.adapter.persistence;

import com.hedza06.springcloud.user.domain.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, Integer> { }

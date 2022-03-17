package com.hedza06.springcloud.user.adapter.persistence;

import com.hedza06.springcloud.user.domain.User;
import com.hedza06.springcloud.user.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    @Query(value = "select new com.hedza06.springcloud.user.dto.UserDTO(user.id, user.fullName, product.productSourceIdentifier) " +
            "from User user " +
            "left join user.products product " +
            "where product.productSourceIdentifier = :productId")
    List<UserDTO> findByProductSourceIdentifier(@Param("productId") String productId);

    Optional<User> findByEmail(String email);
}

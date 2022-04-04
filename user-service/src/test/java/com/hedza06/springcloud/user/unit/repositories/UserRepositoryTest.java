package com.hedza06.springcloud.user.unit.repositories;

import com.hedza06.springcloud.user.dto.UserDTO;
import com.hedza06.springcloud.user.entities.User;
import com.hedza06.springcloud.user.entities.UserProduct;
import com.hedza06.springcloud.user.repositories.UserProductRepository;
import com.hedza06.springcloud.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProductRepository userProductRepository;


    @Test
    @Transactional
    public void shouldReturnUserByGivenCorrectEmail()
    {
        User user = User.builder()
            .fullName("Heril Muratovic")
            .email("test@email.com")
            .address("Test Address")
            .products(Collections.emptyList())
            .build();

        User persistedUser = userRepository.save(user);
        assertThat(persistedUser).isNotNull();
        assertThat(persistedUser.getId()).isNotNull();

        Optional<User> optionalUser = userRepository.findByEmail("test@email.com");
        assertThat(optionalUser).isPresent();

        User userFromDb = optionalUser.get();
        assertThat(userFromDb.getId()).isEqualTo(persistedUser.getId());
        assertThat(userFromDb.getFullName()).isEqualTo(persistedUser.getFullName());
        assertThat(userFromDb.getAddress()).isEqualTo(persistedUser.getAddress());
        assertThat(userFromDb.getEmail()).isEqualTo(persistedUser.getEmail());
        assertThat(userFromDb.getProducts()).isEqualTo(persistedUser.getProducts());
    }

    @Test
    @Transactional
    public void shouldNotReturnUserGivenIncorrectEmail()
    {
        User user = User.builder()
            .fullName("John Wick")
            .email("test-dummy@email.com")
            .address("Test Dummy Address")
            .products(Collections.emptyList())
            .build();

        User persistedUser = userRepository.save(user);
        assertThat(persistedUser).isNotNull();
        assertThat(persistedUser.getId()).isNotNull();

        Optional<User> optionalUser = userRepository.findByEmail("dummy@email.com");
        assertThat(optionalUser).isNotPresent();
    }

    @Test
    @Transactional
    public void shouldReturnUsersByGivenCorrectProductSourceIdentifier()
    {
        User user = User.builder()
            .fullName("John Wick")
            .email("test-dummy@email.com")
            .address("Test Dummy Address")
            .build();

        UserProduct userProduct = new UserProduct(null, user, "PS-001-TEST");
        userProduct.setUser(user);
        user.setProducts(List.of(userProduct));

        User persistedUser = userRepository.save(user);
        assertThat(persistedUser).isNotNull();
        assertThat(persistedUser.getId()).isNotNull();

        userProductRepository.save(userProduct);

        List<UserDTO> users = userRepository.findByProductSourceIdentifier("PS-001-TEST");
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(1);

        UserDTO userDTO = users.get(0);
        assertThat(userDTO.getId()).isEqualTo(persistedUser.getId());
        assertThat(userDTO.getProductSourceIdentifier()).isEqualTo("PS-001-TEST");
    }
}

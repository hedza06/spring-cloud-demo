package com.hedza06.springcloud.user.utils;

import com.hedza06.springcloud.user.dto.ProductDTO;
import com.hedza06.springcloud.user.dto.UserDTO;
import com.hedza06.springcloud.user.entities.User;

import java.util.Collections;
import java.util.List;

public final class TestUtils {

    private TestUtils() { }

    /**
     * Getting users for response entity
     *
     * @return List of UserDTO Objects
     */
    public static List<UserDTO> getMockedUsers()
    {
        UserDTO userOne = new UserDTO(1, "Heril Muratovic", "P-001");
        UserDTO userTwo = new UserDTO(2, "John Doe", "P-001");
        UserDTO userThree = new UserDTO(3, "Jake Doe", "P-001");

        return List.of(userOne, userTwo, userThree);
    }

    /**
     * Getting product for creating new request
     *
     * @return ProductDTO object
     */
    public static ProductDTO getRequestProduct()
    {
        ProductDTO product = new ProductDTO();
        product.setName("P-NEW");
        product.setDescription("D-NEW");
        product.setSourceIdentifier("S-NEW");

        return product;
    }

    /**
     * Get mocked user
     *
     * @return User Entity
     */
    public static User getMockedUser()
    {
        return new User(
            1,
            "Heril Muratovic",
            "test address",
            "hedzaprog@gmail.com",
            Collections.emptyList()
        );
    }
}

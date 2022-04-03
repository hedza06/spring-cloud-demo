package com.hedza06.springcloud.user.unit.services;

import com.hedza06.springcloud.user.dto.UserDTO;
import com.hedza06.springcloud.user.entities.User;
import com.hedza06.springcloud.user.repositories.UserRepository;
import com.hedza06.springcloud.user.services.UserService;
import com.hedza06.springcloud.user.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    public void shouldReturnCollectionOfUsersByGivenProductIdentifier()
    {
        List<UserDTO> mockedUsers = TestUtils.getMockedUsers();
        when(userRepository.findByProductSourceIdentifier(anyString())).thenReturn(mockedUsers);

        List<UserDTO> users = userService.findByProductSource(anyString());
        assertThat(users).isNotNull().isNotEmpty();
        assertThat(users.get(0).getId()).isEqualTo(mockedUsers.get(0).getId());
        assertThat(users.get(0).getFullName()).isEqualTo(mockedUsers.get(0).getFullName());
        assertThat(users.get(0).getProductSourceIdentifier()).isEqualTo(mockedUsers.get(0).getProductSourceIdentifier());

        assertThat(users.get(1).getId()).isEqualTo(mockedUsers.get(1).getId());
        assertThat(users.get(1).getFullName()).isEqualTo(mockedUsers.get(1).getFullName());
        assertThat(users.get(1).getProductSourceIdentifier()).isEqualTo(mockedUsers.get(1).getProductSourceIdentifier());

        assertThat(users.get(2).getId()).isEqualTo(mockedUsers.get(2).getId());
        assertThat(users.get(2).getFullName()).isEqualTo(mockedUsers.get(2).getFullName());
        assertThat(users.get(2).getProductSourceIdentifier()).isEqualTo(mockedUsers.get(2).getProductSourceIdentifier());
    }

    @Test
    public void shouldAssignGivenProductToUserTargetingByEmail()
    {
        String email = "hedzaprog@gmail.com";

        User user = TestUtils.getMockedUser();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        userService.assignProductToUser(email, anyString());
    }
}

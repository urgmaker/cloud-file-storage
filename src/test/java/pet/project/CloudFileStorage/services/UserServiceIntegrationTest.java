package pet.project.CloudFileStorage.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pet.project.CloudFileStorage.dto.UserDto;
import pet.project.CloudFileStorage.models.User;
import pet.project.CloudFileStorage.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceIntegrationTest {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserServiceIntegrationTest(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    void registerNewUserAccount_shouldSaveUserInDatabase() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test_username@mail.ru");
        userDto.setUsername("test_username");
        userDto.setPassword("test_password");

        userService.registerNewUserAccount(userDto);

        Optional<User> user = userRepository.findByUsername("test_username");

        assertTrue(user.isPresent(), "User should exist in the database");
        user.ifPresent(u -> {
            assertEquals("test_username@mail.ru", u.getEmail(), "Email should match");
            assertEquals("test_password", u.getPassword(), "Password should be hashed");
        });
    }
}

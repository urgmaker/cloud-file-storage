package pet.project.CloudFileStorage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.CloudFileStorage.dto.UserDto;
import pet.project.CloudFileStorage.models.User;
import pet.project.CloudFileStorage.repositories.UserRepository;

import static pet.project.CloudFileStorage.models.Role.ROLE_USER;

import java.util.Set;

@Service
@Transactional
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerNewUserAccount(UserDto userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(userDTO.getEmail());
        user.setRoles(Set.of(ROLE_USER));

        userRepository.save(user);
    }
}

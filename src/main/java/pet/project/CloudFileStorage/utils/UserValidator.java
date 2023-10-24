package pet.project.CloudFileStorage.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pet.project.CloudFileStorage.dto.UserDto;
import pet.project.CloudFileStorage.models.User;
import pet.project.CloudFileStorage.services.CustomUserDetailsService;

@Component
public class UserValidator implements Validator {
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public UserValidator(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;

        try {
            customUserDetailsService.loadUserByUsername(userDto.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("username", "", "User name exists");
    }
}

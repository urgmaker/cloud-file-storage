package pet.project.CloudFileStorage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pet.project.CloudFileStorage.dto.UserDto;
import pet.project.CloudFileStorage.services.UserService;
import pet.project.CloudFileStorage.utils.UserValidator;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public RegistrationController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping
    public String showRegistrationPage(@ModelAttribute("userDto") UserDto userDto) {
        return "registration";
    }

    @PostMapping
    public String performRegistration(@ModelAttribute("userDto") UserDto userDto,
                                      BindingResult bindingResult) {
        userValidator.validate(userDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.registerNewUserAccount(userDto);
        return "redirect:/login";
    }
}

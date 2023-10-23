package pet.project.CloudFileStorage.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @Size(min = 2, max = 20, message = "Email should be between 2 and 20")
    @NotBlank(message = "Email should not be empty")
    private String username;

    @Size(min = 6, message = "Password should be bigger than 6 characters")
    @NotBlank(message = "Password should not be empty")
    private String password;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email should not be empty")
    private String email;
}

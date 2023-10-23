package pet.project.CloudFileStorage.services;

import pet.project.CloudFileStorage.dto.UserDto;
import pet.project.CloudFileStorage.models.User;

public interface IUserService {
    User registerNewUserAccount(UserDto userDTO);
}

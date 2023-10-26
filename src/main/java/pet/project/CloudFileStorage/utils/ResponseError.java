package pet.project.CloudFileStorage.utils;

import lombok.experimental.UtilityClass;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

@UtilityClass
public class ResponseError {
    public static String getErrorMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Something wrong");
    }
}

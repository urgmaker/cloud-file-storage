package pet.project.CloudFileStorage.dto.file;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileUploadDto extends FileDto {
    @NotNull(message = "You must specify the file to upload")
    private MultipartFile file;

    public FileUploadDto(String owner, MultipartFile file) {
        super(owner);
        this.file = file;
    }
}

package pet.project.CloudFileStorage.dto.folder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FolderUploadDto extends FolderDto {
    @Size(min = 1, message = "Empty folder upload is not supported")
    @NotNull(message = "You must specify the folder to upload")
    private List<MultipartFile> files;

    public FolderUploadDto(String owner, List<MultipartFile> files) {
        super(owner);
        this.files = files;
    }
}

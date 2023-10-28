package pet.project.CloudFileStorage.dto.file;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileDeleteDto extends FileDto {
    @NotBlank(message = "Can't get the path of the file to delete")
    private String path;

    public FileDeleteDto(String owner, String path) {
        super(owner);
        this.path = path;
    }
}

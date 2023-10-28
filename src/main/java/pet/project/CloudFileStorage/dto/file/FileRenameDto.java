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
public class FileRenameDto extends FileDto {
    @NotBlank(message = "Can't get the current name of the file to rename")
    private String currentName;

    @NotBlank(message = "New name of the file cannot be empty")
    private String newName;

    @NotBlank(message = "Can't get the path of the file to rename")
    private String path;

    public FileRenameDto(String owner, String currentName, String newName, String path) {
        super(owner);
        this.currentName = currentName;
        this.newName = newName;
        this.path = path;
    }
}

package pet.project.CloudFileStorage.dto.folder;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FolderRenameDto extends FolderDto {
    @NotBlank(message = "Can't get the current name of the folder to rename")
    private String currentName;

    @NotBlank(message = "New name of the folder cannot be empty")
    private String newName;

    @NotBlank(message = "Can't get the path of the folder to rename")
    private String path;

    public FolderRenameDto(String owner, String currentName, String newName, String path) {
        super(owner);
        this.currentName = currentName;
        this.newName = newName;
        this.path = path;
    }
}

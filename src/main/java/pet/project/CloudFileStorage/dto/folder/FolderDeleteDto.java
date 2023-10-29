package pet.project.CloudFileStorage.dto.folder;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FolderDeleteDto extends FolderDto {
    @NotBlank(message = "Can't get the path of the folder to delete")
    private String path;

    public FolderDeleteDto(String owner, String path) {
        super(owner);
        this.path = path;
    }
}

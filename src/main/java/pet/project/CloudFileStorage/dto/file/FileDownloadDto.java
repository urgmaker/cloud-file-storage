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
public class FileDownloadDto extends FileDto {
    @NotBlank(message = "The filename is empty")
    private String name;

    @NotBlank(message = "The filepath is empty")
    private String path;

    public FileDownloadDto(String owner, String name, String path) {
        super(owner);
        this.name = name;
        this.path = path;
    }
}

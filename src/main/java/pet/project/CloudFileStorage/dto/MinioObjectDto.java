package pet.project.CloudFileStorage.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class MinioObjectDto {
    private String owner;

    private Boolean isDir;

    private String path;

    private String name;
}

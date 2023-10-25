package pet.project.CloudFileStorage.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "cloud_file_storage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(unique = true, nullable = false)
    @NotBlank
    private String username;
    @Column(nullable = false)
    @NotBlank
    private String password;
    @Column(unique = true, nullable = false)
    @NotBlank
    private String email;

    private Role roles;

    public List<Role> getRoles() {
        return Collections.singletonList(roles);
    }
}

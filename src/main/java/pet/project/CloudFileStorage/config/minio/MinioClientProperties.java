package pet.project.CloudFileStorage.config.minio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "minio.client")
@Getter
@Setter
public class MinioClientProperties {
    private String endpoint;
    private String user;
    private String password;
}

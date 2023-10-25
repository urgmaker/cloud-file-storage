package pet.project.CloudFileStorage.config.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioClientConfiguration {
    private final MinioClientProperties minioClientProperties;

    @Autowired
    public MinioClientConfiguration(MinioClientProperties minioClientProperties) {
        this.minioClientProperties = minioClientProperties;
    }

    @Bean
    public MinioClient configureMinioClient() {
        return MinioClient.builder()
                .endpoint(minioClientProperties.getEndpoint())
                .credentials(minioClientProperties.getUser(), minioClientProperties.getPassword())
                .build();
    }
}

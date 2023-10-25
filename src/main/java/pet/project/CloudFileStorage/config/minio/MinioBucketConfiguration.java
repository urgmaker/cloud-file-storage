package pet.project.CloudFileStorage.config.minio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import pet.project.CloudFileStorage.services.BucketService;

@Configuration
@Getter
@Setter
public class MinioBucketConfiguration {

    private final BucketService bucketService;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Autowired
    public MinioBucketConfiguration(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    public void createBucket() {
        try {
            if (!bucketService.isBucketExists(bucketName)) {
                bucketService.createBucket(bucketName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Bucket '" + bucketName + "' is not created", e);
        }
    }
}

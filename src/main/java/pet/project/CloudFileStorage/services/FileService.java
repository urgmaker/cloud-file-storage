package pet.project.CloudFileStorage.services;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pet.project.CloudFileStorage.config.minio.MinioBucketConfiguration;
import pet.project.CloudFileStorage.dto.file.FileDownloadDto;
import pet.project.CloudFileStorage.dto.file.FileUploadDto;
import pet.project.CloudFileStorage.exceptions.file.FileOperationException;

import java.io.InputStream;

import static pet.project.CloudFileStorage.utils.MinioRootFolder.getUserRootFolderPrefix;

@Service
public class FileService {
    private final MinioClient minioClient;
    private final MinioBucketConfiguration minioBucketConfiguration;

    @Autowired
    public FileService(MinioClient minioClient, MinioBucketConfiguration minioBucketConfiguration) {
        this.minioClient = minioClient;
        this.minioBucketConfiguration = minioBucketConfiguration;
    }

    public ByteArrayResource downloadFile(FileDownloadDto fileDownloadDto) {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(minioBucketConfiguration.getBucketName())
                .object(getUserRootFolderPrefix(fileDownloadDto.getOwner()) + fileDownloadDto.getPath())
                .build();
        try (GetObjectResponse object = minioClient.getObject(getObjectArgs)) {
            return new ByteArrayResource(object.readAllBytes());
        } catch (Exception e) {
            throw new FileOperationException("Download error! Please, try again later.");
        }
    }

    public void uploadFile(FileUploadDto fileUploadDto) {
        MultipartFile file = fileUploadDto.getFile();
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(inputStream, file.getSize(), -1)
                    .bucket(minioBucketConfiguration.getBucketName())
                    .object(getUserRootFolderPrefix(fileUploadDto.getOwner()) + file.getOriginalFilename())
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("There is an error while uploading the file, try again later");
        }
    }
}

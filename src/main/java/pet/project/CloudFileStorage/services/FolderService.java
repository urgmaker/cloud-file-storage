package pet.project.CloudFileStorage.services;

import io.minio.MinioClient;
import io.minio.SnowballObject;
import io.minio.UploadSnowballObjectsArgs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pet.project.CloudFileStorage.config.minio.MinioBucketConfiguration;
import pet.project.CloudFileStorage.dto.folder.FolderUploadDto;
import pet.project.CloudFileStorage.exceptions.folder.FolderOperationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pet.project.CloudFileStorage.utils.MinioRootFolder.getUserRootFolderPrefix;

@Service
public class FolderService {
    private final FileService fileService;
    private final MinioClient minioClient;
    private final MinioBucketConfiguration minioBucketConfiguration;

    public FolderService(FileService fileService, MinioClient minioClient, MinioBucketConfiguration minioBucketConfiguration) {
        this.fileService = fileService;
        this.minioClient = minioClient;
        this.minioBucketConfiguration = minioBucketConfiguration;
    }

    public void uploadFolder(FolderUploadDto folderUploadDto) {
        List<MultipartFile> files = folderUploadDto.getFiles();
        String owner = folderUploadDto.getOwner();
        try {
            List<SnowballObject> objects = convertToUploadObject(files, owner);

            minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder()
                    .bucket(minioBucketConfiguration.getBucketName())
                    .objects(objects)
                    .build());
        } catch (Exception e) {
            throw new FolderOperationException("There is an error while uploading the folder, try again later");
        }
    }

    private List<SnowballObject> convertToUploadObject(List<MultipartFile> files, String owner) throws IOException {
        List<SnowballObject> objects = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
                continue;
            }
            SnowballObject snowballObject = new SnowballObject(
                    getUserRootFolderPrefix(owner) + file.getOriginalFilename(),
                    file.getInputStream(),
                    file.getSize(),
                    null
            );
            objects.add(snowballObject);
        }
        return objects;
    }
}

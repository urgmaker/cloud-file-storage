package pet.project.CloudFileStorage.services;

import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pet.project.CloudFileStorage.config.minio.MinioBucketConfiguration;
import pet.project.CloudFileStorage.dto.MinioObjectDto;
import pet.project.CloudFileStorage.dto.file.FileDeleteDto;
import pet.project.CloudFileStorage.dto.file.FileDownloadDto;
import pet.project.CloudFileStorage.dto.file.FileRenameDto;
import pet.project.CloudFileStorage.dto.file.FileUploadDto;
import pet.project.CloudFileStorage.exceptions.file.FileOperationException;

import java.io.InputStream;
import java.util.List;

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

    public List<MinioObjectDto> getAllUserFiles(String username, String folder) {
        // TODO
        return null;
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

    public void renameFile(FileRenameDto fileRenameDto) {
        try {
            minioClient.copyObject(CopyObjectArgs.builder()
                    .bucket(minioBucketConfiguration.getBucketName())
                    .object(getPathWithNewName(
                            getUserRootFolderPrefix(fileRenameDto.getOwner() + fileRenameDto.getPath()),
                            fileRenameDto.getCurrentName(),
                            fileRenameDto.getNewName()
                    ))
                    .source(CopySource.builder()
                            .bucket(minioBucketConfiguration.getBucketName())
                            .object(getUserRootFolderPrefix(fileRenameDto.getOwner() + fileRenameDto.getPath()))
                            .build())
                    .build());

            FileDeleteDto fileDeleteDto = new FileDeleteDto();

            fileDeleteDto.setPath(fileRenameDto.getPath());
            fileDeleteDto.setOwner(fileRenameDto.getOwner());

            deleteFile(fileDeleteDto);
        } catch (Exception e) {
            throw new FileOperationException("There is an error while renaming the file, try again later");
        }
    }

    public void deleteFile(FileDeleteDto fileDeleteDto) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioBucketConfiguration.getBucketName())
                    .object(getUserRootFolderPrefix(fileDeleteDto.getOwner()) + fileDeleteDto.getPath())
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("There is an error while deleting the file, try again later");
        }
    }

    private static String getFileNameFromPath(String path) {
        if (!path.contains("/")) {
            return path;
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (path.lastIndexOf('/') > 0) {
            return path.substring(path.lastIndexOf('/') + 1);
        }
        return path;
    }

    private static String getPathWithNewName(String path, String oldName, String newName) {
        return path.replace(oldName, newName);
    }
}

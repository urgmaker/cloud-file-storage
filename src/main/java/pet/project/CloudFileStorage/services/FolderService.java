package pet.project.CloudFileStorage.services;

import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pet.project.CloudFileStorage.config.minio.MinioBucketConfiguration;
import pet.project.CloudFileStorage.dto.MinioObjectDto;
import pet.project.CloudFileStorage.dto.folder.FolderDeleteDto;
import pet.project.CloudFileStorage.dto.folder.FolderRenameDto;
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

    public void renameFolder(FolderRenameDto folderRenameDto) {
        List<MinioObjectDto> files = fileService.getAllUserFiles(folderRenameDto.getOwner(), folderRenameDto.getPath());

        for (MinioObjectDto file : files) {
            try {
                minioClient.copyObject(CopyObjectArgs.builder()
                        .bucket(minioBucketConfiguration.getBucketName())
                        .object(getPathWithNewFolder(
                                getUserRootFolderPrefix(file.getOwner()) + file.getPath(),
                                folderRenameDto.getCurrentName(),
                                folderRenameDto.getNewName()
                        ))
                        .source(CopySource.builder()
                                .bucket(minioBucketConfiguration.getBucketName())
                                .object(getUserRootFolderPrefix(file.getOwner()) + file.getPath())
                                .build())
                        .build());

                FolderDeleteDto folderDeleteDto = new FolderDeleteDto();

                folderDeleteDto.setPath(folderDeleteDto.getPath());
                folderDeleteDto.setOwner(folderDeleteDto.getOwner());

                deleteFolder(folderDeleteDto);
            } catch (Exception e) {
                throw new FolderOperationException("There is an error while renaming the folder, try again later");
            }
        }
    }

    public void deleteFolder(FolderDeleteDto folderDeleteDto) {
        List<MinioObjectDto> files = fileService.getAllUserFiles(folderDeleteDto.getOwner(), folderDeleteDto.getPath());

        List<DeleteObject> objects = convertToDeleteObjects(files);

        Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder()
                .bucket(minioBucketConfiguration.getBucketName())
                .objects(objects)
                .build());

        results.forEach(deleteErrorResult -> {
            try {
                deleteErrorResult.get();
            } catch (Exception e) {
                throw new FolderOperationException("There is an error while deleting the folder, try again later");
            }
        });
    }
}

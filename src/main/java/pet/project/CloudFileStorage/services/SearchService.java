package pet.project.CloudFileStorage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.project.CloudFileStorage.dto.MinioObjectDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchService {

    private final FileService fileService;

    @Autowired
    public SearchService(FileService fileService) {
        this.fileService = fileService;
    }

    public List<MinioObjectDto> search(String username, String query) {
        List<MinioObjectDto> files = fileService.getAllUserFiles(username, "");

        List<MinioObjectDto> foundFiles = files.stream()
                .filter(file -> file.getName().contains(query))
                .toList();

        Set<MinioObjectDto> foundFolders = findMatchingFolders(files, query);

        List<MinioObjectDto> results = new ArrayList<>();

        results.addAll(foundFiles);
        results.addAll(foundFolders);

        return results;
    }

    private static Set<MinioObjectDto> findMatchingFolders(List<MinioObjectDto> objects, String query) {
        objects = removeFileNamesFromPath(objects);

        Set<MinioObjectDto> matchingFolders = new HashSet<>();

        for (MinioObjectDto folder : objects) {
            String path = folder.getPath();
            String[] parts = path.split("/");

            StringBuilder currentPath = new StringBuilder();

            for (String part : parts) {
                currentPath.append(part).append("/");
                if (part.contains(query)) {
                    matchingFolders.add(new MinioObjectDto(
                            folder.getOwner(),
                            true,
                            currentPath.toString(),
                            part
                    ));
                }
            }
        }

        return matchingFolders;
    }

    private static List<MinioObjectDto> removeFileNamesFromPath(List<MinioObjectDto> files) {
        return files.stream()
                .peek(f -> {
                    String path = f.getPath();
                    String name = f.getName();

                    f.setPath(path.replace(name, ""));
                })
                .toList();
    }
}

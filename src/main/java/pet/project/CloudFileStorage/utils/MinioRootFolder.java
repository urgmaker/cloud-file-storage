package pet.project.CloudFileStorage.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MinioRootFolder {
    public static String getUserRootFolderPrefix(String username) {
        return "user-" + username + "-files/";
    }

    public static String removeUserRootFolderPrefix(String path, String username) {
        String rootFolder = getUserRootFolderPrefix(username);

        return path.replaceAll(rootFolder, "");
    }
}

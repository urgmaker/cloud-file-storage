# Cloud File Storage

Multi-user file cloud. Service users can use it to upload and store files.

## Features
- Upload, rename, delete and download files & folders
- Deep recursive files search

## Build with
- Java, Spring Boot, PostgreSQL, Gradle
- MinIO, Docker, Redis, Thymeleaf, Testcontainers

## Acknowledgements
Minio does not have folders. Everything in minio is an "object".
Folders are created based on the / - forward slashes in the filename

- Folders in the app may work unpredictably
- There is not possible to download empty folder to the app
- Maximum size of one file to upload - 500 MB
- Maximum size of multiple files to upload is also - 500 MB
- Currently all files are uploaded to the root directory (this behavior will be fixed in the future)
- It is not possible to select multiple files or folders to download at once (this behavior will be fixed in the future)
- It is possible to change extension of the downloaded file in the renmaing option
- Search will list all files and folders that contains search query. Broad search queries like "a" can produce very large list of results
- Frontend is not adapted to the mobile platforms
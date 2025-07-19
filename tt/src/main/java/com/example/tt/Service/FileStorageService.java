package com.example.tt.Service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService() {
        // Chemin absolu du répertoire d'upload
        this.fileStorageLocation = Paths.get("./uploads")
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new FileStorageException("Could not create upload directory", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Validation du fichier
        if (file == null || file.isEmpty()) {
            throw new FileStorageException("Failed to store empty file");
        }

        try {
            // Nettoyage du nom de fichier
            String originalFileName = StringUtils.cleanPath(
                    Objects.requireNonNull(file.getOriginalFilename()));

            // Génération d'un nom de fichier unique pour éviter les collisions
            String fileExtension = "";
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex > 0) {
                fileExtension = originalFileName.substring(dotIndex);
            }
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // Vérification des chemins malveillants
            if (uniqueFileName.contains("..")) {
                throw new FileStorageException(
                        "Filename contains invalid path sequence: " + uniqueFileName);
            }

            // Copie du fichier vers la cible
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return uniqueFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Failed to store file", ex);
        }
    }

    public Resource loadFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("Could not read file: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("Could not read file: " + fileName, ex);
        }
    }

    // Exception personnalisée pour une meilleure gestion des erreurs
    public static class FileStorageException extends RuntimeException {
        public FileStorageException(String message) {
            super(message);
        }

        public FileStorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
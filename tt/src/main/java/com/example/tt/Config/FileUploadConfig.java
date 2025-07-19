package com.example.tt.Config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class FileUploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        // Taille maximale autorisée pour un fichier (20 Mo)
        factory.setMaxFileSize(DataSize.ofMegabytes(20));

        // Taille totale de la requête multipart (50 Mo)
        factory.setMaxRequestSize(DataSize.ofMegabytes(50));

        // Seuil à partir duquel les fichiers sont écrits sur disque
        factory.setFileSizeThreshold(DataSize.ofMegabytes(1)); // tu peux ajuster

        return factory.createMultipartConfig();
    }
}

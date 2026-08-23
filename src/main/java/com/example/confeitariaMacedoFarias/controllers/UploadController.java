package com.example.confeitariaMacedoFarias.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.confeitariaMacedoFarias.services.CloudinaryService;



@RestController
@RequestMapping("/upload")
public class UploadController {


    private final CloudinaryService cloudinaryService;


    public UploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }


    @PostMapping
    public ResponseEntity<?> upload(
            @RequestParam("file") MultipartFile file
    ) {

        System.out.println("========= ENTROU CONTROLLER UPLOAD =========");

        System.out.println("Arquivo: " + file.getOriginalFilename());
        System.out.println("Tipo: " + file.getContentType());
        System.out.println("Tamanho: " + file.getSize());


        String url = cloudinaryService.uploadImage(file);


        System.out.println("URL FINAL CLOUDINARY:");
        System.out.println(url);


        return ResponseEntity.ok(
                new UploadResponse(url)
        );
    }


    public record UploadResponse(
            String imageUrl
    ) {}
}
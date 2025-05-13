package com.example.projectc1023i1.controller.admin;

import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
@RestController
@RequestMapping("/image")
public class ImageController {
    @GetMapping("/product/{link}")
    public ResponseEntity<?> getImage(@PathVariable("link") String link) {
        try {
            Path imagePath = Paths.get("uploads/product/"+link);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }else {
                return ResponseEntity.notFound().build();
            }
        }catch ( Exception e ) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/deal/{link}")
    public ResponseEntity<?> getImages(@PathVariable("link") String link) {
        try {
            Path imagePath = Paths.get("uploads/deal_coupon/"+link);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }else {
                return ResponseEntity.notFound().build();
            }
        }catch ( Exception e ) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/collection/{link}")
    public ResponseEntity<?> getImagesCollection(@PathVariable("link") String link) {
        try {
            Path imagePath = Paths.get("uploads/collection/"+link);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }else {
                return ResponseEntity.notFound().build();
            }
        }catch ( Exception e ) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{link}")
    public ResponseEntity<?> getImagesUser(@PathVariable("link") String link) {
        try {
            Path imagePath = Paths.get("uploads/user/"+link);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }else {
                return ResponseEntity.notFound().build();
            }
        }catch ( Exception e ) {
            return ResponseEntity.notFound().build();
        }
    }
}

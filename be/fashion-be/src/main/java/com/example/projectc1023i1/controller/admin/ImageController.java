package com.example.projectc1023i1.controller.admin;

import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@RequestMapping("/image")
@RestController()
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
}

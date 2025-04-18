package com.example.projectc1023i1.controller.comment;

import com.example.projectc1023i1.Dto.get_data.FormData;
import com.example.projectc1023i1.model.Users;
import com.example.projectc1023i1.service.impl.IFeedbackMessService;
import com.example.projectc1023i1.service.impl.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequestMapping("/api/comment/")
@RestController
public class CommentRestController {
    @Autowired
    private IFeedbackService feedbackService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private IFeedbackMessService feedbackMessService;
    @PostMapping(value = "upload-image-feedback", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@AuthenticationPrincipal Users users,
                                          @RequestParam("unique") String unique ,
                                          @RequestPart("files") List<MultipartFile> files
    ) throws IOException {
        feedbackService.uploadMediaComment(files,unique);
        int count= 0;
        for (MultipartFile file : files) {
            count++;
        }
        return ResponseEntity.ok("success");
    }

    @PostMapping(value = "upload-mess-media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMessMedia(@AuthenticationPrincipal Users users,
                                             @RequestParam("unique") String unique,
                                             @RequestPart("files") List<MultipartFile> files) {
        feedbackMessService.saveFeedbackImageMessRespone(files,unique);
        return ResponseEntity.ok("success");
    }

    @GetMapping( "media")
    public ResponseEntity<?> getImage(
            @RequestParam("link") String link
    ) throws IOException {
        try {
            Path filePath = Paths.get(uploadDir).resolve(link).normalize();

            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            // Xác định content type (image/jpeg, video/mp4,...)
            String contentType = Files.probeContentType(filePath);

            byte[] fileBytes = Files.readAllBytes(filePath);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(fileBytes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("e.getMessage()");
        }
    }


}

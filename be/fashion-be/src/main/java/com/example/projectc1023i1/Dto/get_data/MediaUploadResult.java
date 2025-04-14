package com.example.projectc1023i1.Dto.get_data;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaUploadResult {
    private String originalFileName;
    private boolean success;
    private String fileUrl;
    private String fileType; // "IMAGE" hoặc "VIDEO"
    private long fileSize;
    private String errorMessage;
}

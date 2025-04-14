package com.example.projectc1023i1.Dto.get_data;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormData {
    private List<MultipartFile> files;// base64
}

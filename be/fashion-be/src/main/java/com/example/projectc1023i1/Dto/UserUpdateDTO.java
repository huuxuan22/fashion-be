package com.example.projectc1023i1.Dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO implements Serializable {
    private Boolean gender;
    private String numberphone;
    private String fullName;
    private LocalDateTime birthday;
    private MultipartFile image;
    private String email;
}

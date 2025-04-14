package com.example.projectc1023i1.Dto.get_data;

import com.example.projectc1023i1.model.FeedbackMedia;
import com.example.projectc1023i1.model.FeedbackMessage;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
    private Integer feedbackId;
    private  String title;
    private String content;
    private Boolean status;
    private Integer rating;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private UserSimpleDTO user;
    private ProductSmpleDTO product;
    private List<FeedbackMessageDTO> feedbackMessages;
    private List<FeedbackMediaDTO> feedbackMedia;
}


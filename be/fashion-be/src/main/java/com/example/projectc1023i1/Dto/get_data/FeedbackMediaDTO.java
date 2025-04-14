package com.example.projectc1023i1.Dto.get_data;

import com.example.projectc1023i1.model.Feedback;
import com.example.projectc1023i1.model.FeedbackMessage;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedbackMediaDTO {
    private Long mediaId;
    private Feedback feedback;
    private FeedbackMessage fbMessage;
    private String mediaUrl;
    private String mediaType;
    private Timestamp createdAt;
}

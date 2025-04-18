package com.example.projectc1023i1.Dto.get_data;

import com.example.projectc1023i1.model.Feedback;
import com.example.projectc1023i1.model.Users;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackMessageDTO {
    private Long fbMessageId;
    private Users sender;
    private String message;
    private Timestamp createdAt;
}

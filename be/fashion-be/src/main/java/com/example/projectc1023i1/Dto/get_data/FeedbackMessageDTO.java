package com.example.projectc1023i1.Dto.get_data;

import com.example.projectc1023i1.model.Users;
import lombok.*;

import java.sql.Timestamp;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackMessageDTO {
    private Long fbMessageId;
    private Users sender;
    private String message;
    private Timestamp createdAt;
}

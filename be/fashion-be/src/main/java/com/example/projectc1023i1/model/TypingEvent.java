package com.example.projectc1023i1.model;

import com.example.projectc1023i1.Validation.Categories.NotExistSubCateId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NotExistSubCateId
@Builder
public class TypingEvent {
    private String userId;
    private Boolean isTyping;
    private String productId;

}

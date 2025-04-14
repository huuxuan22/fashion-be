package com.example.projectc1023i1.controller.comment;

import com.example.projectc1023i1.Exception.UserExepion;
import com.example.projectc1023i1.model.Comment;
import com.example.projectc1023i1.model.Feedback;
import com.example.projectc1023i1.model.TypingEvent;
import com.example.projectc1023i1.service.impl.IFeedbackService;
import com.example.projectc1023i1.service.impl.IProductService;
import com.example.projectc1023i1.service.impl.IUserService;
import com.example.projectc1023i1.utils.UniqueCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class CommentController {
    @Autowired
    private IFeedbackService feedbackService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IUserService userService;
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @MessageMapping("/comments")
    @SendTo("/topic/comments")
    public Comment handleFeedbackWithAttachments( Comment comment) throws UserExepion {
        String unique = UniqueCodeGenerator.generateUniqueCode();
        feedbackService.save(Feedback.builder()
                        .content(comment.getComment())
                        .title("binh luan")
                        .rating(5)
                        .product(productService.getProductById(Integer.valueOf(comment.getProductId())))
                        .user(userService.findUserById(Integer.valueOf(comment.getSender())))
                        .uniqueValue(unique)
                        .createAt(LocalDateTime.now())
                        .updateAt(LocalDateTime.now())
                        .status(true)
                .build());
        logger.info("Received comment: {}", comment.getComment());
        comment.setUnique(unique);
        return comment;
    }



    @MessageMapping("/comment/upload/{productId}")
    public void handleFileUpload(
            @DestinationVariable String productId,
            @Payload byte[] fileData,
            @Header("fileName") String fileName,
            @Header("simpSessionId") String sessionId
    ) {
        // Xử lý file ảnh
        // Lưu file và trả về URL
    }


    // Xử lý sự kiện typing
    @MessageMapping("/typing")
    @SendTo("/topic/typing")
    public TypingEvent handleTyping(TypingEvent typingEvent) {
        logger.info("User {} is typing: {}", typingEvent.getUserId(), typingEvent.getIsTyping());
        return typingEvent;
    }
}

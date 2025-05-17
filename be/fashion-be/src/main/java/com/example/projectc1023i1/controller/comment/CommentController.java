package com.example.projectc1023i1.controller.comment;

import com.example.projectc1023i1.Dto.FeedbackMessDTO;
import com.example.projectc1023i1.Dto.get_data.FeedbackMessageDTO;
import com.example.projectc1023i1.Exception.UserExepion;
import com.example.projectc1023i1.model.Comment;
import com.example.projectc1023i1.model.Feedback;
import com.example.projectc1023i1.model.FeedbackMessage;
import com.example.projectc1023i1.model.TypingEvent;
import com.example.projectc1023i1.service.impl.IFeedbackMessService;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class CommentController {
    @Autowired
    private IFeedbackService feedbackService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IFeedbackMessService feedbackMessService;
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @MessageMapping("/comments")
    @SendTo("/topic/comments")
    public Comment handleFeedbackWithAttachments(Comment comment) throws UserExepion {
        String unique = UniqueCodeGenerator.generateUniqueCode();
        feedbackService.save(Feedback.builder()
                        .content(comment.getComment())
                        .title("binh luan")
                        .rating(comment.getRating())
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


    @MessageMapping("/response")
    @SendTo("/topic/comments")
    public FeedbackMessDTO handleSendResponeMessage (FeedbackMessDTO feedbackMessDTO) throws UserExepion {
        String unique = UniqueCodeGenerator.generateUniqueCode();
        Feedback feedbackFind = feedbackService.findById(feedbackMessDTO.getFeedbackId());
        feedbackMessService.saveFeedbackMessage(FeedbackMessage.builder()
                        .sender(userService.findUserById(feedbackMessDTO.getSender()))
                        .message(feedbackMessDTO.getMessage())
                        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                        .uniqueValue(unique)
                        .feedback(feedbackFind)
                .build());
        feedbackMessDTO.setUnique(unique);
        return feedbackMessDTO;
    }

    // Xử lý sự kiện typing
    @MessageMapping("/typing")
    @SendTo("/topic/typing")
    public TypingEvent handleTyping(TypingEvent typingEvent) {
        logger.info("User {} is typing: {}", typingEvent.getUserId(), typingEvent.getIsTyping());
        return typingEvent;
    }

}

package com.example.projectc1023i1.controller.comment;

import com.example.projectc1023i1.model.Comment;
import com.example.projectc1023i1.model.TypingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @MessageMapping("/comment")
    @SendTo("/topic/comments")
    public Comment sendComment(Comment comment) {
        logger.info("Received comment: {}", comment.getComment());
        return comment;
    }


    // Xử lý sự kiện typing
    @MessageMapping("/typing")
    @SendTo("/topic/typing")
    public TypingEvent handleTyping(TypingEvent typingEvent) {
        logger.info("User {} is typing: {}", typingEvent.getUserId(), typingEvent.getIsTyping());
        return typingEvent;
    }
}

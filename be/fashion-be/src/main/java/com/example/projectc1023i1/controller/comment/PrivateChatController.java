package com.example.projectc1023i1.controller.comment;

import com.example.projectc1023i1.model.PrivateChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class PrivateChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(PrivateChat.class);

    @MessageMapping("/private-message")
    public void sendPrivateMessage(PrivateChat message, Principal principal) {
        String to = message.getReceiverId(); // user bạn muốn gửi tới
        logger.info("Sending private message to " + to);
        messagingTemplate.convertAndSendToUser(
                to, "/queue/messages", message); // gửi riêng tới user đó
    }
}

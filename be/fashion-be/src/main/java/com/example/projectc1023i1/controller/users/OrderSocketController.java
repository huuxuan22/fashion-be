package com.example.projectc1023i1.controller.users;

import com.example.projectc1023i1.Dto.OrderDetailDTO;
import com.example.projectc1023i1.Dto.ProductDetailDTO;
import com.example.projectc1023i1.Dto.PurchaseDTO;
import com.example.projectc1023i1.Exception.UserExepion;
import com.example.projectc1023i1.component.JwtTokenUtils;
import com.example.projectc1023i1.controller.comment.CommentController;
import com.example.projectc1023i1.model.Comment;
import com.example.projectc1023i1.model.Feedback;
import com.example.projectc1023i1.model.Users;
import com.example.projectc1023i1.service.RedisService;
import com.example.projectc1023i1.service.TokenService;
import com.example.projectc1023i1.service.impl.IOrderService;
import com.example.projectc1023i1.service.impl.IUserService;
import com.example.projectc1023i1.utils.UniqueCodeGenerator;
import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OrderSocketController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private  UserDetailsService userDetailsService;
    @Autowired
    private IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(OrderSocketController.class);
    @MessageMapping("/purchase")
    @SendTo("/topic/purchase")
    public PurchaseDTO purchase(PurchaseDTO purchaseDTO)  {
        String username = jwtTokenUtils.extractUserName(purchaseDTO.getToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails instanceof Users) {
            Users user = (Users) userDetails;
            logger.info("User: {}", user.toString());
            orderService.saveOrder(purchaseDTO.getOrderDetail(),user,purchaseDTO.getProductDetail());
        }

        logger.info("Chi tiet: {}", purchaseDTO.getProductDetail().size());
        logger.info("Chi tiet: {}", purchaseDTO.getOrderDetail().toString());
        return purchaseDTO;
    }
}

package com.example.projectc1023i1.controller.users;

import com.example.projectc1023i1.Dto.get_data.CountComment;
import com.example.projectc1023i1.Dto.get_data.FeedbackDTO;
import com.example.projectc1023i1.model.Feedback;
import com.example.projectc1023i1.model.Users;
import com.example.projectc1023i1.service.impl.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    private IFeedbackService feedbackService;

    @GetMapping("/product")
    public ResponseEntity<?> getFeedback(@AuthenticationPrincipal Users user,
                                         @RequestParam("productId") Integer productId,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size,
                                         @RequestParam(value = "rating", defaultValue = "0") Integer rating) {
        try {
            if (user == null || productId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng đang bị null hoặc chưa đăng nhập");
            }
            if (size <= 0 || page <= 0) {
                size = 10;
                page = 0;
            }
            Pageable pageable =  PageRequest.of(page,size);
            Page<FeedbackDTO> feedbackPage = feedbackService.getFeedbacks(productId,pageable,rating);
//        feedbackService.getFeedbacks()
            return new ResponseEntity<>(feedbackPage, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/count-comment")
    public ResponseEntity<?> countComment(@AuthenticationPrincipal Users user,
                                         @RequestParam("productId") Integer productId) {
        if (user == null || productId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng đang bị null hoặc chưa đăng nhập");
        }
        List<CountComment> countCommentList = feedbackService.coutComment(productId);
//        feedbackService.getFeedbacks()
        return new ResponseEntity<>(countCommentList, HttpStatus.OK);
    }

    @GetMapping("/count-all-comment")
    public ResponseEntity<?> getAllComment(@AuthenticationPrincipal Users user,
                                          @RequestParam("productId") Integer productId) {
        if (user == null || productId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng đang bị null hoặc chưa đăng nhập");
        }
        Integer countCommentList = feedbackService.getAllComment(productId);
//        feedbackService.getFeedbacks()
        return new ResponseEntity<>(countCommentList, HttpStatus.OK);
    }

    @GetMapping("/count-all-media")
    public ResponseEntity<?> getAllMedia(@AuthenticationPrincipal Users user,
                                           @RequestParam("productId") Integer productId) {
        if (user == null || productId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng đang bị null hoặc chưa đăng nhập");
        }
        Integer countCommentList = feedbackService.getAllMedia(productId);
//        feedbackService.getFeedbacks()
        return new ResponseEntity<>(countCommentList, HttpStatus.OK);
    }

}

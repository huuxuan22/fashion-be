package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.model.FeedbackMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFeedbackMessService {
    void saveFeedbackMessage(FeedbackMessage message);
    void saveFeedbackImageMessRespone(List<MultipartFile> fileList,String unique);
    List<FeedbackMessage> getAllByFeedbackId(Integer feedbackId);
}

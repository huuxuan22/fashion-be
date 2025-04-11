package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.get_data.CountComment;
import com.example.projectc1023i1.Dto.get_data.FeedbackDTO;
import com.example.projectc1023i1.model.Feedback;
import com.example.projectc1023i1.repository.impl.IFeedbackRepo;
import com.example.projectc1023i1.service.impl.IFeedbackService;
import com.example.projectc1023i1.service.mapper.IFeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService implements IFeedbackService {
    @Autowired
    private IFeedbackRepo feedbackRepo;

    @Autowired
    private IFeedbackMapper feedbackMapper;
    @Override
    public Page<FeedbackDTO> getFeedbacks(Integer productId, Pageable pageable,Integer rating) {
        Page<Feedback> feedbackPage = null;
        if (rating <= 0) {
            feedbackPage = feedbackRepo.findAllByProductId(productId, pageable);
        }else  {
            feedbackPage = feedbackRepo.findAllByProductIdAndRatings(productId, rating,pageable);
        }
        return feedbackMapper.toFeedbackDTOPage(feedbackPage);
    }

    @Override
    public List<CountComment> coutComment(Integer productId) {
        return (List<CountComment>) feedbackRepo.countCommentByProductId(productId);
    }

    @Override
    public Integer getAllComment(Integer ProductId) {
        return Math.toIntExact(feedbackRepo.countAllCommentsAndFeedback(ProductId));
    }

    @Override
    public Integer getAllMedia(Integer ProductId) {
        return feedbackRepo.countAllMediaByProductId(ProductId);
    }
}

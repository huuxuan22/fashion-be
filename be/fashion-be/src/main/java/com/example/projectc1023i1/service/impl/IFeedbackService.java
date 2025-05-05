package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.Dto.get_data.CountComment;
import com.example.projectc1023i1.Dto.get_data.FeedbackDTO;
import com.example.projectc1023i1.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFeedbackService {
    Page<FeedbackDTO> getFeedbacks(Integer productId, Pageable pageable,Integer rating);
    List<CountComment> coutComment(Integer ProductId);
    Integer getAllComment(Integer ProductId);
    Integer getAllMedia(Integer ProductId);
    void save(Feedback feedback);
    void uploadMediaComment (List<MultipartFile> files,String ProductId);
    Feedback findById(Integer id);
    Integer getTotalPage( Integer productId,Integer rating);
    Double getAverageRating(Integer productId);
    Integer getAllRatingByProductId( Integer productId);
}

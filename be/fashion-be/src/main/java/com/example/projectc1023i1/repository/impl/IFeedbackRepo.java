package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFeedbackRepo extends JpaRepository<Feedback, Integer> {

    @Query("select f from Feedback f where f.product.productId = :productId ")
    Page<Feedback> findAllByProductId(@Param("productId") Integer productId,
                                      Pageable pageable);

    @Query("select f from Feedback f where f.product.productId = :productId and f.rating = :rating")
    Page<Feedback> findAllByProductIdAndRatings(@Param("productId") Integer productId,
                                                @Param("rating") Integer rating,Pageable pageable);

    @Query("select f.rating,count(fm.fbMessageId)+1 as count from Feedback f " +
            "inner join FeedbackMessage as fm on fm.feedback.feedbackId = f.feedbackId where f.product.productId =:productId group by f.rating")
    List<?> countCommentByProductId(@Param("productId") Integer productId);

    @Query("select count(f) + " +
            "(select count(fm) from FeedbackMessage fm where fm.feedback.product.productId = :productId) " +
            "from Feedback f where f.product.productId = :productId")
    Long countAllCommentsAndFeedback(@Param("productId") Integer productId);

    @Query(value = "SELECT COUNT(DISTINCT fm.media_id) AS total_media\n" +
            "FROM feedback_media fm\n" +
            "LEFT JOIN feedbacks f1 ON fm.feedback_id = f1.feedback_id\n" +
            "LEFT JOIN feedbacks_message fmsg ON fm.fb_message_id = fmsg.fb_message_id\n" +
            "LEFT JOIN feedbacks f2 ON fmsg.feedback_id = f2.feedback_id\n" +
            "WHERE (f1.product_id = :productId OR f2.product_id = :productId)",nativeQuery = true)
    Integer countAllMediaByProductId(@Param("productId") Integer productId);
}

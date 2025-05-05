package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IFeedbackRepo extends JpaRepository<Feedback, Integer> {
    @Query("SELECT  f FROM Feedback f WHERE f.product.productId = :productId ")
    Page<Feedback> findAllByProductId(
            @Param("productId") Integer productId,
            Pageable pageable
    );


    @Query("select f from Feedback f where f.product.productId = :productId and f.rating = :rating ")
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

    @Query(value = "select f.* from feedbacks as f where f.unique_value like :unique",nativeQuery = true)
    Feedback findFeedbackByUnique(@Param("unique") String uniqueKey);
    @Modifying
    @Transactional
    @Query("delete from Feedback where uniqueValue = :unique")
    void deleteFeedbackByUniqueKey(@Param("unique") String uniqueKey);
    @Query("select count(*) from Feedback where product.productId = :productId ")
    Integer getTotalPage(@Param("productId") Integer productId);

    @Query("select count(*) from Feedback where product.productId = :productId and rating = :rating")
    Integer getTotalPageByRating(@Param("productId") Integer productId, @Param("rating") Integer rating);
    @Query( value = "select sum(rating * count) / sum(count) AS average_rating\n" +
            "from (select rating, COUNT(*) AS count from feedbacks\n" +
            "\twhere product_id = :productId\n" +
            "    group by  rating\n" +
            ") as rating_counts",nativeQuery = true)
    Double getAverageRating (@Param("productId") Integer productId);

    @Query("select count(f) from Feedback f where f.product.productId = :productId")
    Integer getAllRatingByProductId(@Param("productId") Integer productId);
}

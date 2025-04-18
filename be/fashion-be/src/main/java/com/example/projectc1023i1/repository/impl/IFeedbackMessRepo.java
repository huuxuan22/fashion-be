package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.FeedbackMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IFeedbackMessRepo extends JpaRepository<FeedbackMessage,Integer> {

    @Transactional
    @Modifying
    @Query("delete from  FeedbackMessage where uniqueValue = :unique")
    void deleteByFeedbackUnique(@Param("unique") String unique);
    @Query("select fm from FeedbackMessage fm where fm.uniqueValue like :unique")
    FeedbackMessage findByUniqueValue(@Param("unique") String unique);
    @Query("select fm from FeedbackMessage fm where fm.feedback.feedbackId = :feedbackId")
    List<FeedbackMessage> findAllByFeedbackIs(@Param("feedbackId") Integer feedbackId);
}

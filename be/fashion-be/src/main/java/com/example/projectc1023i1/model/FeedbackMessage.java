package com.example.projectc1023i1.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "feedbacks_message")
public class FeedbackMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fb_message_id")
    private Long fbMessageId;

    @ManyToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Users sender;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "unique_value")
    private String uniqueValue;

    @OneToMany(mappedBy = "fbMessage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FeedbackMedia> feedbackMedia;
}

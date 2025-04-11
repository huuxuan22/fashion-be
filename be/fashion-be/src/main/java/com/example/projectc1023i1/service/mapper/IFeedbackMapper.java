package com.example.projectc1023i1.service.mapper;

import com.example.projectc1023i1.Dto.get_data.FeedbackDTO;
import com.example.projectc1023i1.Dto.get_data.FeedbackMediaDTO;
import com.example.projectc1023i1.Dto.get_data.FeedbackMessageDTO;
import com.example.projectc1023i1.model.Feedback;
import com.example.projectc1023i1.model.FeedbackMedia;
import com.example.projectc1023i1.model.FeedbackMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface IFeedbackMapper {
    @Mapping(source = "user", target = "user")
    @Mapping(source = "product", target = "product")
    @Mapping(source = "feedbackMessages", target = "feedbackMessages")
    @Mapping(source = "feedbackMedia", target = "feedbackMedia")
    FeedbackDTO toFeedbackDTO(Feedback feedback);
    FeedbackMessageDTO toFeedbackMessageDTO(FeedbackMessage feedbackMessage);
    FeedbackMediaDTO toFeedbackMediaDTO(FeedbackMedia feedbackMedia);

    default Page<FeedbackDTO> toFeedbackDTOPage(Page<Feedback> feedbackPage) {
        return feedbackPage.map(this::toFeedbackDTO);
    }
}

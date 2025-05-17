package com.example.projectc1023i1.service.mapper;

import com.example.projectc1023i1.Dto.get_data.*;
import com.example.projectc1023i1.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface IFeedbackMapper {
    @Mapping(source = "feedbackMessages", target = "feedbackMessages")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "product", target = "product")
    @Mapping(source = "feedbackMedia", target = "feedbackMedia")
    FeedbackDTO toFeedbackDTO(Feedback feedback);
    FeedbackMessageDTO toFeedbackMessageDTO(FeedbackMessage message);
    UserSimpleDTO toUserSimpleDTO(Users user);
    ProductSmpleDTO toProductSimpleDTO(Product product);
    FeedbackMediaDTO toFeedbackMediaDTO(FeedbackMedia feedbackMedia);


    default Page<FeedbackDTO> toFeedbackDTOPage(Page<Feedback> feedbackPage) {
        return feedbackPage.map(this::toFeedbackDTO);
    }

}

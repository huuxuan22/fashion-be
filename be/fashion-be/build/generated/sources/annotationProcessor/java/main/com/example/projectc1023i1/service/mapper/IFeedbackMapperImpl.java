package com.example.projectc1023i1.service.mapper;

import com.example.projectc1023i1.Dto.get_data.FeedbackDTO;
import com.example.projectc1023i1.Dto.get_data.FeedbackMediaDTO;
import com.example.projectc1023i1.Dto.get_data.FeedbackMessageDTO;
import com.example.projectc1023i1.Dto.get_data.ProductSmpleDTO;
import com.example.projectc1023i1.Dto.get_data.UserSimpleDTO;
import com.example.projectc1023i1.model.Feedback;
import com.example.projectc1023i1.model.FeedbackMedia;
import com.example.projectc1023i1.model.FeedbackMessage;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.Users;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-12T04:43:42+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class IFeedbackMapperImpl implements IFeedbackMapper {

    @Override
    public FeedbackDTO toFeedbackDTO(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }

        FeedbackDTO.FeedbackDTOBuilder feedbackDTO = FeedbackDTO.builder();

        feedbackDTO.user( usersToUserSimpleDTO( feedback.getUser() ) );
        feedbackDTO.product( productToProductSmpleDTO( feedback.getProduct() ) );
        feedbackDTO.feedbackMessages( feedbackMessageListToFeedbackMessageDTOList( feedback.getFeedbackMessages() ) );
        feedbackDTO.feedbackMedia( feedbackMediaListToFeedbackMediaDTOList( feedback.getFeedbackMedia() ) );
        feedbackDTO.feedbackId( feedback.getFeedbackId() );
        feedbackDTO.title( feedback.getTitle() );
        feedbackDTO.content( feedback.getContent() );
        feedbackDTO.status( feedback.getStatus() );
        feedbackDTO.rating( feedback.getRating() );
        feedbackDTO.createAt( feedback.getCreateAt() );
        feedbackDTO.updateAt( feedback.getUpdateAt() );

        return feedbackDTO.build();
    }

    @Override
    public FeedbackMessageDTO toFeedbackMessageDTO(FeedbackMessage feedbackMessage) {
        if ( feedbackMessage == null ) {
            return null;
        }

        FeedbackMessageDTO.FeedbackMessageDTOBuilder feedbackMessageDTO = FeedbackMessageDTO.builder();

        feedbackMessageDTO.fbMessageId( feedbackMessage.getFbMessageId() );
        feedbackMessageDTO.sender( feedbackMessage.getSender() );
        feedbackMessageDTO.message( feedbackMessage.getMessage() );
        feedbackMessageDTO.createdAt( feedbackMessage.getCreatedAt() );

        return feedbackMessageDTO.build();
    }

    @Override
    public FeedbackMediaDTO toFeedbackMediaDTO(FeedbackMedia feedbackMedia) {
        if ( feedbackMedia == null ) {
            return null;
        }

        FeedbackMediaDTO.FeedbackMediaDTOBuilder feedbackMediaDTO = FeedbackMediaDTO.builder();

        feedbackMediaDTO.mediaId( feedbackMedia.getMediaId() );
        feedbackMediaDTO.feedback( feedbackMedia.getFeedback() );
        feedbackMediaDTO.fbMessage( feedbackMedia.getFbMessage() );
        feedbackMediaDTO.mediaUrl( feedbackMedia.getMediaUrl() );
        feedbackMediaDTO.mediaType( feedbackMedia.getMediaType() );
        feedbackMediaDTO.createdAt( feedbackMedia.getCreatedAt() );

        return feedbackMediaDTO.build();
    }

    protected UserSimpleDTO usersToUserSimpleDTO(Users users) {
        if ( users == null ) {
            return null;
        }

        UserSimpleDTO.UserSimpleDTOBuilder userSimpleDTO = UserSimpleDTO.builder();

        userSimpleDTO.userId( users.getUserId() );
        userSimpleDTO.imgUrl( users.getImgUrl() );
        userSimpleDTO.numberphone( users.getNumberphone() );
        userSimpleDTO.fullName( users.getFullName() );

        return userSimpleDTO.build();
    }

    protected ProductSmpleDTO productToProductSmpleDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductSmpleDTO.ProductSmpleDTOBuilder productSmpleDTO = ProductSmpleDTO.builder();

        productSmpleDTO.productId( product.getProductId() );
        productSmpleDTO.productName( product.getProductName() );

        return productSmpleDTO.build();
    }

    protected List<FeedbackMessageDTO> feedbackMessageListToFeedbackMessageDTOList(List<FeedbackMessage> list) {
        if ( list == null ) {
            return null;
        }

        List<FeedbackMessageDTO> list1 = new ArrayList<FeedbackMessageDTO>( list.size() );
        for ( FeedbackMessage feedbackMessage : list ) {
            list1.add( toFeedbackMessageDTO( feedbackMessage ) );
        }

        return list1;
    }

    protected List<FeedbackMediaDTO> feedbackMediaListToFeedbackMediaDTOList(List<FeedbackMedia> list) {
        if ( list == null ) {
            return null;
        }

        List<FeedbackMediaDTO> list1 = new ArrayList<FeedbackMediaDTO>( list.size() );
        for ( FeedbackMedia feedbackMedia : list ) {
            list1.add( toFeedbackMediaDTO( feedbackMedia ) );
        }

        return list1;
    }
}

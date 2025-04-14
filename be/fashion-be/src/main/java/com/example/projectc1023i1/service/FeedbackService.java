package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.get_data.CountComment;
import com.example.projectc1023i1.Dto.get_data.FeedbackDTO;
import com.example.projectc1023i1.Exception.IOException;
import com.example.projectc1023i1.Exception.PayloadTooLargeException;
import com.example.projectc1023i1.Exception.UnsuportedMediaTypeException;
import com.example.projectc1023i1.model.Feedback;
import com.example.projectc1023i1.model.FeedbackMedia;
import com.example.projectc1023i1.repository.impl.IFeedbackMediaRepo;
import com.example.projectc1023i1.repository.impl.IFeedbackRepo;

import com.example.projectc1023i1.service.impl.IFeedbackService;
import com.example.projectc1023i1.service.mapper.IFeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.common.io.Files.getFileExtension;

@Service
public class FeedbackService implements IFeedbackService {
    @Autowired
    private IFeedbackRepo feedbackRepo;

    @Autowired
    private IFeedbackMapper feedbackMapper;

    @Autowired
    private IFeedbackMediaRepo feedbackMediaRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;
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

    @Override
    public void save(Feedback feedback) {
        feedbackRepo.save(feedback);
    }

    @Override
    public void uploadMediaComment(List<MultipartFile> files,String unique) {
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String contentType = file.getContentType();
                String originalName = file.getOriginalFilename();

                // ✅ Kiểm tra định dạng
                if (!isSupportedContentType(contentType) || !isSupportedExtension(originalName)) {
                    feedbackRepo.deleteFeedbackByUniqueKey(unique);
                    throw  new UnsuportedMediaTypeException("Định dạng file không được hỗ trợ:");
                }

                // ✅ Kích thước tối đa 10MB
                if (file.getSize() > 10 * 1024 * 1024) {
                    feedbackRepo.deleteFeedbackByUniqueKey(unique);
                    System.out.println("File quá lớn: " + originalName);
                    throw  new PayloadTooLargeException("File quá lớn");
                }

                // ✅ Xác định thư mục lưu
                String folder = determineFolder(contentType); // vd: "images" hoặc "videos"
                String extension = "." + getFileExtension(originalName); // .jpg .mp4 ...
                String fileName = UUID.randomUUID() + extension;

                Path storagePath = Paths.get(uploadDir, folder).toAbsolutePath().normalize();
                Files.createDirectories(storagePath);

                Path filePath = storagePath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // ✅ Tạo đường dẫn URL để lưu vào DB
                String fileUrl = String.format( folder+"/"+ fileName);
                fileUrls.add(fileUrl);
                FeedbackMedia feedbackMedia = FeedbackMedia.builder()
                        .feedback(feedbackRepo.findFeedbackByUnique(unique))
                        .fbMessage(null)
                        .mediaType("image")
                        .mediaUrl(fileUrl)
                        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                        .build();
                feedbackMediaRepo.save(feedbackMedia);

            } catch (java.io.IOException e) {
                feedbackRepo.deleteFeedbackByUniqueKey(unique);
                e.printStackTrace();
            }
        }
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/gif") ||
                        contentType.equals("video/mp4") ||
                        contentType.equals("video/mpeg")
        );
    }

    private String determineFolder(String contentType) {
        if (contentType == null) return "others";
        if (contentType.startsWith("image")) return "images";
        if (contentType.startsWith("video")) return "videos";
        return "others";
    }

    private boolean isSupportedExtension(String fileName) {
        if (fileName == null) return false;
        String lowerName = fileName.toLowerCase();
        return lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")
                || lowerName.endsWith(".png") || lowerName.endsWith(".gif")
                || lowerName.endsWith(".mp4") || lowerName.endsWith(".mpeg");
    }

}

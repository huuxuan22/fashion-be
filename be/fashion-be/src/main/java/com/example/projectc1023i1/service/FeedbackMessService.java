package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Exception.PayloadTooLargeException;
import com.example.projectc1023i1.Exception.UnsuportedMediaTypeException;
import com.example.projectc1023i1.model.FeedbackMedia;
import com.example.projectc1023i1.model.FeedbackMessage;
import com.example.projectc1023i1.repository.impl.IFeedbackMediaRepo;
import com.example.projectc1023i1.repository.impl.IFeedbackMessRepo;
import com.example.projectc1023i1.repository.impl.IFeedbackRepo;
import com.example.projectc1023i1.service.impl.IFeedbackMessService;
import com.example.projectc1023i1.service.impl.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.google.common.io.Files.getFileExtension;


@Service
public class FeedbackMessService implements IFeedbackMessService {
    @Autowired
    private IFeedbackMessRepo feedbackMessRepo;
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    private IFeedbackRepo feedbackRepo;
    @Autowired
    private IFeedbackMediaRepo feedbackMediaRepo;


    @Override
    public void saveFeedbackMessage(FeedbackMessage message) {
        feedbackMessRepo.save(message);
    }
    @Override
    public void saveFeedbackImageMessRespone(List<MultipartFile> fileList, String unique) {
        for (MultipartFile file : fileList) {
            try {
                String contentType = file.getContentType();
                String originalFilename = file.getOriginalFilename();
                if (!isSupportedContentType(contentType) || !isSupportedExtension(originalFilename)) {
                    feedbackMessRepo.deleteByFeedbackUnique(unique);
                    throw new UnsuportedMediaTypeException("Không hỗ trợ loại file này ");
                }
                if (file.getSize() > 10* 1024 * 1024) {
                    feedbackMessRepo.deleteByFeedbackUnique(unique);
                    throw new PayloadTooLargeException("Kích thước của file này lớn hơn 100MB");
                }

                String folder = determineFolder(contentType); // vd: "images" hoặc "videos"
                String extension = "." + getFileExtension(originalFilename); // .jpg .mp4 ...
                String fileName = UUID.randomUUID() + extension;


                Path storagePath = Paths.get(uploadDir, folder).toAbsolutePath().normalize(); // tao duong dan luu file
                Files.createDirectories(storagePath);
                Path filePath = storagePath.resolve(fileName);
                String fileSave = String.format(folder+"/"+ fileName);
                feedbackMediaRepo.save(FeedbackMedia.builder()
                                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                                .mediaType("Phản hồi")
                                .mediaUrl(fileSave)
                                .fbMessage(feedbackMessRepo.findByUniqueValue(unique))
                        .build());
            } catch (Exception e) {
                feedbackMessRepo.deleteByFeedbackUnique(unique);
            }
        }
    }

    @Override
    public List<FeedbackMessage> getAllByFeedbackId(Integer feedbackId) {
        return feedbackMessRepo.findAllByFeedbackIs(feedbackId);
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

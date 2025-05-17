package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.DealDTO;
import com.example.projectc1023i1.Exception.IOException;
import com.example.projectc1023i1.Exception.PayloadTooLargeException;
import com.example.projectc1023i1.Exception.UnsuportedMediaTypeException;
import com.example.projectc1023i1.model.Deal;
import com.example.projectc1023i1.model.Notifcation;
import com.example.projectc1023i1.repository.impl.IDealRepo;
import com.example.projectc1023i1.repository.impl.INotificationRepo;
import com.example.projectc1023i1.repository.impl.IProductRepo;
import com.example.projectc1023i1.service.impl.IDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DealService implements IDealService {
    @Autowired
    private IDealRepo dealRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private IProductRepo productRepo;
    @Autowired
    private INotificationRepo notificationRepo;
    @Override
    @Transactional
    @Modifying
    public void save(DealDTO dealDTO) throws java.io.IOException {

        String filename = "";
        if (dealDTO.getImageFile() != null) {
            filename = storeFile(dealDTO.getImageFile());
            MultipartFile file = dealDTO.getImageFile();
            if (file.getSize() > 1024 * 1024) {
                throw  new PayloadTooLargeException("anh qua lon, lon hon 10Byte");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.contains("image/")) {
                throw  new UnsuportedMediaTypeException("Không hỗ trợ loại ảnh này ");
            }
        }
        List<Deal> dealList = new ArrayList<>();
        List<Notifcation> notifcationList = new ArrayList<>();
        for (Integer product : dealDTO.getProduct()) {
            Deal deal = Deal.builder()
                    .dealPrice(dealDTO.getDealPrice())
                    .dealType(dealDTO.getDealPrice() != null ? "DEAL PRICE" : "DEAL PERCENT")
                    .product(productRepo.findById(product).get())
                    .discountPercent(dealDTO.getDiscountPercent())
                    .startTime(dealDTO.getStartTime())
                    .endTime(dealDTO.getEndTime())
                    .dealStatus("CREATE")
                    .imageUrl(filename)
                    .build();

            Notifcation notifcation = Notifcation.builder()
                    .deal(deal)
                    .message("DEAL NEW")
                    .status(false)
                    .createAt(LocalDateTime.now())
                    .build();
            notifcationList.add(notifcation);
            dealList.add(deal);
        }
        dealRepo.saveAll(dealList);
        notificationRepo.saveAll(notifcationList);
    }

    @Override
    public Page<Deal> findAll(Pageable pageable) {
        return dealRepo.findAll(pageable);
    }

    @Override
    public void delete(Integer dealId
    ) {
        dealRepo.deleteDeal(dealId);
    }

    @Override
    public void updateStatus(Deal deal) {
        dealRepo.updateDealStatus(deal.getDealId(),deal.getDealStatus());
    }

    @Override
    public Integer getAllPageOfDeals() {
        return dealRepo.getAllPage() % 4 == 0 ? dealRepo.getAllPage() / 4 : dealRepo.getAllPage() / 4 + 1;
    }

    @Override
    public List<Deal> findByProduct(Integer productId) {
        return dealRepo.findByProduct(productId);
    }

    @Override
    public void decreaseOneDealQuality(List<Deal> deals) {

    }

    public String storeFile(MultipartFile file) throws IOException, java.io.IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new com.example.projectc1023i1.Exception.IOException("Ảnh bạn chọn không phù hợp ");
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFileName = UUID.randomUUID().toString() + "-" + fileName;
        Path uploadDir = Paths.get("uploads/deal_coupon");
        if (!Files.exists(uploadDir)) {
            Files.createDirectory(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    public static boolean isImageFile(MultipartFile file) {
        if (file == null || file.getContentType() == null) {
            return false;
        }
        String contentType = file.getContentType().toLowerCase();
        return contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif") ||
                contentType.equals("image/jpg");
    }
}

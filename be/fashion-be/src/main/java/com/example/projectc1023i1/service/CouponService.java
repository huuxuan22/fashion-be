package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.CouponDTO;
import com.example.projectc1023i1.Exception.IOException;
import com.example.projectc1023i1.Exception.PayloadTooLargeException;
import com.example.projectc1023i1.Exception.UnsuportedMediaTypeException;
import com.example.projectc1023i1.model.Coupon;
import com.example.projectc1023i1.model.Notifcation;
import com.example.projectc1023i1.repository.impl.ICouponRepo;
import com.example.projectc1023i1.repository.impl.INotificationRepo;
import com.example.projectc1023i1.service.impl.ICouponService;
import com.example.projectc1023i1.service.impl.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class CouponService implements ICouponService {
    @Autowired
    private ICouponRepo couponRepo;

    @Autowired
    private INotificationRepo notificationRepo;

    @Override
    public void createCoupon(CouponDTO couponDTO) throws java.io.IOException {
        String fileName = "";
        if (couponDTO.getImageUrl() != null) {
            MultipartFile file = couponDTO.getImageUrl();
            if (file.getSize() > 1024 * 1024) {
                throw  new PayloadTooLargeException("anh qua lon, lon hon 10Byte");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.contains("image/")) {
                throw  new UnsuportedMediaTypeException("Không hỗ trợ loại ảnh này ");
            }
            fileName = storeFile(couponDTO.getImageUrl());

        }
        Coupon coupon = Coupon.builder()
                .couponStatus(couponDTO.getCouponStatus())
                .couponCode(couponDTO.getCouponCode())
                .endTime(couponDTO.getEndTime())
                .startTime(couponDTO.getStartTime())
                .imageUrl(fileName)
                .discountType(couponDTO.getDiscountType())
                .couponStatus(couponDTO.getCouponStatus())
                .discountValue(couponDTO.getDiscountValue())
                .usageLimit(couponDTO.getUsageLimit())
                .build();
        Notifcation notifcation = Notifcation.builder()
                .createAt(LocalDateTime.now())
                .message("COUPON NEW")
                .status(false)
                .coupon(coupon)
                .build();
        couponRepo.save(coupon);
        notificationRepo.save(notifcation);

    }

    @Override
    public Page<Coupon> getCoupons(Pageable pageable) {
        return couponRepo.findAll(pageable);
    }

    @Override
    public void deleteCoupon(Long couponId) {

    }

    @Override
    public void cancelCoupon(Coupon coupon) {
        if (coupon.getCouponStatus().equals("Cancel")) {
            coupon.setCouponStatus("Active");
        }else {
            coupon.setCouponStatus("Cancel");
        }
        couponRepo.save(coupon);
    }

    @Override
    public Integer countTotalCoupons() {
        return couponRepo.countCoupon() % 5 == 0 ? couponRepo.countCoupon() / 5 : couponRepo.countCoupon() / 5 + 1;
    }

    @Override
    public Coupon findByCouponCode(String param) {
        return couponRepo.findByCouponCode(param);
    }

    @Override
    public void decreaseOneCouponQuality(Coupon coupon) {
        coupon.setUsageLimit(coupon.getUsageLimit() - 1);
        couponRepo.save(coupon);
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

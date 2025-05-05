package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.OrderDetailDTO;
import com.example.projectc1023i1.Dto.ProductDetailDTO;
import com.example.projectc1023i1.Dto.ProductVariantDTO;
import com.example.projectc1023i1.model.*;
import com.example.projectc1023i1.repository.impl.*;
import com.example.projectc1023i1.service.impl.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepo orderRepo;
    @Autowired
    private ICommuneRepo communeRepo;
    @Autowired
    private IProductRepo productRepo;
    @Autowired
    private IProductVariantRepo productVariantRepo;
    @Autowired
    private IProvinceRepo provinceRepo;
    @Autowired
    private IDistrictRepo districtRepo;
    @Autowired
    private IAddressUserRepo addressUserRepo;
    @Autowired
    private IOrderDetailRepo orderDetailRepo;

    @Override
    @Transactional
    @Modifying
    public void saveOrder(OrderDetailDTO orderDetailDTO, Users users, List<ProductDetailDTO> productDetailDTOS) {
        try {
            String orderCode = generateOrderCode();
            Order order = Order.builder()
                    .orderDate(LocalDateTime.now())
                    .users(users)
                    .total(orderDetailDTO.getTotalPrice())
                    .paymentType(orderDetailDTO.getPaymentType())
                    .status(OrderStatus.valueOf(orderDetailDTO.getStatus()))
                    .note(orderDetailDTO.getNote())
                    .orderCode(orderCode)
                    .build();
            orderRepo.save(order);


            for (ProductDetailDTO dto : productDetailDTOS) {
                ProductVariant variant = ProductVariant.builder()
                        .size(dto.getSize())
                        .color(dto.getColor())
                        .product(dto.getProduct())
                        .price(Double.valueOf(dto.getProduct().getPrice())) // assuming it's already Double
                        .sku(dto.getSize().getNameSize() + "-" +
                                dto.getColor().getColorCode() + "-" +
                                dto.getProduct().getCategories().getSubCategoryName())
                        .build();
                OrderDetails orderDetails = OrderDetails.builder()
                        .productVariant(productVariantRepo.findQuanlityByProductIdAndSizeIdAndColorId(
                                dto.getProduct().getProductId(), dto.getSize().getSizeId(), dto.getColor().getColorId()))
                        .price(Double.valueOf(dto.getProduct().getPrice()))
                        .price(Double.valueOf(dto.getProduct().getPrice()))
                        .quality(dto.getStock())
                        .order(orderRepo.findByOrderCode(orderCode))
                        .build();
                orderDetailRepo.save(orderDetails);
            }
            Province province = getOrSaveProvince(orderDetailDTO.getProvince());
            District district = getOrSaveDistrict(orderDetailDTO.getDistrict());
            Commune commune = getOrSaveCommune(orderDetailDTO.getCommune());
            AddressUser addressUser = AddressUser.builder()
                    .user(users)
                    .province(province)
                    .district(district)
                    .commune(commune)
                    .phone(orderDetailDTO.getNumberPhone())
                    .homeAddress(orderDetailDTO.getStreet())
                    .build();
            addressUserRepo.save(addressUser);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving order", e);
        }
    }

    // Helper methods
    private Province getOrSaveProvince(Province province) {
        return provinceRepo.existsProvinceByCode(province.getCode())
                ? provinceRepo.findProvinceByCode(province.getCode())
                : provinceRepo.save(province);
    }

    private District getOrSaveDistrict(District district) {
        return districtRepo.existsDistrictByCode(district.getCode())
                ? districtRepo.findDistrictByCode(district.getCode())
                : districtRepo.save(district);
    }

    private Commune getOrSaveCommune(Commune commune) {
        return communeRepo.existsCommuneByCode(commune.getCode())
                ? communeRepo.findCommuneByCode(commune.getCode())
                : communeRepo.save(commune);
    }
    public static String generateOrderCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
        String timestamp = sdf.format(new Date());
        String randomStr = getRandomAlphaString(3); // 3 ký tự chữ cái
        return "ORD-" + timestamp + "-" + randomStr;
    }

    private static String getRandomAlphaString(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }
        return sb.toString();
    }
}


package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.OrderDetailDTO;
import com.example.projectc1023i1.Dto.ProductDetailDTO;
import com.example.projectc1023i1.Dto.ProductVariantDTO;
import com.example.projectc1023i1.Dto.get_data.order_maptruck.OrderMaptruck;
import com.example.projectc1023i1.model.*;
import com.example.projectc1023i1.repository.impl.*;
import com.example.projectc1023i1.service.impl.IOrderService;
import com.example.projectc1023i1.service.mapper.IOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
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
    @Autowired
    private IUserRepository userRepo;
    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private INotificationRepo notificationRepo;
    @Override
    @Transactional
    @Modifying
    public void saveOrder(OrderDetailDTO orderDetailDTO, Users users, List<ProductDetailDTO> productDetailDTOS) {
        try {
            List<ProductVariant> productVariantList = new ArrayList<>();
            String orderCode = generateOrderCode();
            Order order = Order.builder()
                    .orderDate(LocalDateTime.now())
                    .users(users)
                    .total(orderDetailDTO.getTotalPrice())
                    .paymentType(orderDetailDTO.getPaymentType())
                    .status("CREATE")
                    .address(
                            orderDetailDTO.getStreet() + ", " + orderDetailDTO.getCommune().getName() + ", "
                            + orderDetailDTO.getDistrict().getName() + ", " + orderDetailDTO.getProvince().getName()
                    )
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
                ProductVariant productVariant = productVariantRepo.findQuanlityByProductIdAndSizeIdAndColorId(
                        dto.getProduct().getProductId(), dto.getSize().getSizeId(), dto.getColor().getColorId());

                Product product = productRepo.findById(productVariant.getProduct().getProductId()).get();
                product.setQuality(product.getQuality() - dto.getStock());
                productVariant.setStock(productVariant.getStock() - dto.getStock());
                productVariantList.add(productVariant);
                productRepo.save(product); // tru di so luong da mua trong da 1 don hang
                orderDetailRepo.save(orderDetails);
            }
            productVariantRepo.saveAll(productVariantList); //tru di so luong da mua trong da 1 don hang
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

    @Override
    public Page<OrderMaptruck> findAllOrderAdmin(Pageable pageable,String category) {
        Page<OrderMaptruck> orderMaptrucks = null;
        if (category.equals("CREATE")) {
            orderMaptrucks = orderMapper.toFeedbackDTOPage(orderRepo.findAllOrderAdminByCREATE(pageable));
        } else if (category.equals("DELIVERY")) {
            orderMaptrucks = orderMapper.toFeedbackDTOPage(orderRepo.findAllOrderAdminByDELIVERy(pageable));
        }else {
            orderMaptrucks = orderMapper.toFeedbackDTOPage(orderRepo.findAllOrderAdmin(pageable));
        }
        return orderMaptrucks;
    }

    @Override
    public Integer countOrder(String param) {
        if (param.equals("")) {
            return orderRepo.countOrderALL()%8==0 ? orderRepo.countOrderALL()/8 : orderRepo.countOrderALL()/8 + 1;
        }
        return orderRepo.countOrder(param)%8==0 ?orderRepo.countOrder(param)/8 : orderRepo.countOrder(param)/8 + 1;
    }

    @Override
    public Integer countOrderALLUser(Integer userId) {
        return orderRepo.countOrderALLUser(userId) % 8 == 0 ? orderRepo.countOrderALLUser(userId)/8 : orderRepo.countOrderALLUser(userId)/8 + 1;
    }

    @Override
    public void completeOrder(Long orderId) {
        Order order = orderRepo.findById(orderId).get();
        Users users = userRepo.findById(order.getUsers().getUserId()).get();
        Notifcation notifcation = Notifcation.builder()
                .message("Đơn hàng của bạn đã hoàn thành")
                .order(order)
                .user(users)
                .status(false)
                .createAt(LocalDateTime.now())
                .build();
        orderRepo.completeOrder(orderId);
        notificationRepo.save(notifcation);
    }

    @Override
    public void deliveryOrder(Long orderId) {
        Order order = orderRepo.findById(orderId).get();
        Users users = userRepo.findById(order.getUsers().getUserId()).get();
        Notifcation notifcation = Notifcation.builder()
                .message("Đơn hàng của bạn đã được vận chuyển")
                .order(order)
                .user(users)
                .status(false)
                .createAt(LocalDateTime.now())
                .build();
        orderRepo.deliveryOrder(orderId);
        notificationRepo.save(notifcation);
    }

    @Override
    public Page<OrderMaptruck> findAllOrderUser(Pageable pageable, Integer userId) {
        Page<Order> orders = orderRepo.findAllOrderUser(pageable, userId);
        return orderMapper.toFeedbackDTOPage(orders);
    }

    @Override
    public void cancelOrder(Integer userId, Long orderId) {
        orderRepo.cancelOrder(userId,orderId);
    }

    @Override
    public boolean existsByOrderIdAndUser(Long orderId, Integer userId) {
        return orderRepo.existsByOrderIdAndUser(orderId, userId);
    }

    @Override
    public Optional<Boolean> isOrderCancelled(Long orderId, Integer userId) {
        return orderRepo.isOrderCancelled(orderId, userId);
    }

    @Override
    public Page<OrderMaptruck> findAllOrderAdmin(Pageable pageable) {
        Page<Order> orders = orderRepo.findAllOrderAdmin(pageable);
        return orderMapper.toFeedbackDTOPage(orders);
    }

    @Override
    public Page<OrderMaptruck> findAllOrderCompleteUser(Pageable pageable, String searchTerm, LocalDateTime startDate, LocalDateTime endDate,String paymentMethod) {
        Page<OrderMaptruck> orderMaptrucks = null;
        if (paymentMethod.equals("all")) {
            paymentMethod = "";
        }
        if (endDate == null && startDate == null) {
            orderMaptrucks = orderMapper.toFeedbackDTOPage(orderRepo.findAllOrderAdminByCOMPLETE0(pageable, searchTerm, paymentMethod));
        } else if (endDate == null || startDate == null) {
            orderMaptrucks = orderMapper.toFeedbackDTOPage(orderRepo.findAllOrderAdminByCOMPLETE1(pageable, searchTerm, startDate, paymentMethod));
        } else {
            orderMaptrucks = orderMapper.toFeedbackDTOPage(orderRepo.findAllOrderAdminByCOMPLETE2(pageable, searchTerm, startDate, endDate, paymentMethod));
        }
        return orderMaptrucks;
    }

    @Override
    public Integer getOrdersCompleteAdmin(String searchTerm, LocalDateTime startDate, LocalDateTime endDate,String paymentMethod) {
        return orderRepo.countOrderAdminByCOMPLETE(searchTerm,startDate,endDate,paymentMethod) %10 == 0 ?
                orderRepo.countOrderAdminByCOMPLETE(searchTerm,startDate,endDate,paymentMethod)/10 : orderRepo.countOrderAdminByCOMPLETE(searchTerm,startDate,endDate,paymentMethod) /10 + 1;
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


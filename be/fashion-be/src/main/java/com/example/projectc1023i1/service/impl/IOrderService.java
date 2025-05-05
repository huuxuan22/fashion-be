package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.Dto.OrderDetailDTO;
import com.example.projectc1023i1.Dto.ProductDetailDTO;
import com.example.projectc1023i1.Dto.ProductVariantDTO;
import com.example.projectc1023i1.model.Order;
import com.example.projectc1023i1.model.OrderDetails;
import com.example.projectc1023i1.model.Users;

import java.util.List;

public interface IOrderService {
    void saveOrder(OrderDetailDTO orderDetailDTO, Users users, List<ProductDetailDTO> productDetailDTOS);
}

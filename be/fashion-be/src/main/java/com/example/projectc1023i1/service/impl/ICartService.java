package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.Dto.ProductDetailDTO;
import com.example.projectc1023i1.Dto.get_data.cart_mapstruck.CartMapper;
import com.example.projectc1023i1.model.Cart;
import com.example.projectc1023i1.model.CartItem;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.Users;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICartService {
    void addProductVariantToCart(Users user, ProductDetailDTO productDetailDTO);
    void addProductToCart(Users user, Product product);
    List<CartMapper> findByUserId(Long userId);
    void deleteByCart( Long cartId);
}

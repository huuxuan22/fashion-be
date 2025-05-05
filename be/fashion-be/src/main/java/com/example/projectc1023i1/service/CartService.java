package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.ProductDetailDTO;
import com.example.projectc1023i1.Dto.get_data.cart_mapstruck.CartMapper;
import com.example.projectc1023i1.model.*;
import com.example.projectc1023i1.repository.impl.ICartItemRepo;
import com.example.projectc1023i1.repository.impl.ICartRepo;
import com.example.projectc1023i1.repository.impl.IProductRepo;
import com.example.projectc1023i1.repository.impl.IProductVariantRepo;
import com.example.projectc1023i1.service.impl.ICartService;
import com.example.projectc1023i1.service.mapper.ICartItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService {
    @Autowired
    private ICartRepo cartRepo;

    @Autowired
    private IProductVariantRepo productVariantRepo;

    @Autowired
    private ICartItemRepo cartItemRepo;

    @Autowired
    private ICartItemMapper cartItemMapper;
    @Override
    public void addProductVariantToCart(Users user, ProductDetailDTO productDetailDTO) {
        ProductVariant productVariant = productVariantRepo.findQuanlityByProductIdAndSizeIdAndColorId(
                productDetailDTO.getProduct().getProductId(),
                productDetailDTO.getSize().getSizeId(),
                productDetailDTO.getColor().getColorId()
        );
        List<CartItem> cartItems = new ArrayList<>();
        Cart cart = Cart.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
            CartItem cartItem = CartItem.builder()
                    .quantity(productDetailDTO.getStock())
                    .cart(cart)
                    .productVariant(productVariant)
                    .build();
            cartItems.add(cartItem);
        cart.setItems(cartItems);
        cartRepo.save(cart);
    }

    @Override
    public void addProductToCart(Users user, Product product) {
        Cart cart = Cart.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = CartItem.builder()
                .quantity(1)
                .cart(cart) 
                .product(product)
                .build();
        cartItems.add(cartItem);
        cart.setItems(cartItems);
        cartRepo.save(cart);
    }

    @Override
    public List<CartMapper> findByUserId(Long userId) {
        List<Cart> carts = cartItemRepo.findByUserId(userId);
        return cartItemMapper.toCartMapperList(carts);
    }

    @Override
    public void deleteByCart(Long cartId) {
        cartRepo.deleteById(cartId);
    }
}

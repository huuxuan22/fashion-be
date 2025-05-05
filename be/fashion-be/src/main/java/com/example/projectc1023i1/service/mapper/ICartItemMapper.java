package com.example.projectc1023i1.service.mapper;

import com.example.projectc1023i1.Dto.get_data.FeedbackDTO;
import com.example.projectc1023i1.Dto.get_data.cart_mapstruck.CartItemMapper;
import com.example.projectc1023i1.Dto.get_data.cart_mapstruck.CartMapper;
import com.example.projectc1023i1.Dto.get_data.cart_mapstruck.ProductMapper;
import com.example.projectc1023i1.Dto.get_data.cart_mapstruck.ProductVariantMapper;
import com.example.projectc1023i1.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICartItemMapper {
    @Mapping(source = "cartId", target = "cartId")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "items", target = "items")
    CartMapper toCartMapper(Cart cart);

    // Chuyển đổi CartItem thành CartItemMapper DTO
    @Mapping(source = "cartItemId", target = "cartItemId")
    @Mapping(source = "productVariant", target = "productVariant")
    @Mapping(source = "product", target = "product")
    @Mapping(source = "quantity", target = "quantity")
    CartItemMapper toCartItemMapper(CartItem cartItem);

    // Chuyển đổi Product thành ProductMapper DTO
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "isActive", target = "isActive")
    @Mapping(source = "thumbnail", target = "thumbnail")
    @Mapping(source = "quality", target = "quality")
    @Mapping(source = "sellPrice", target = "sellPrice")
    @Mapping(source = "price", target = "price")
    ProductMapper toProductMapper(Product product);

    // Chuyển đổi ProductVariant thành ProductVariantMapper DTO
    @Mapping(source = "productVariantId", target = "productVariantId")
    @Mapping(source = "sku", target = "sku")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "stock", target = "stock")
    @Mapping(source = "product", target = "product")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "size", target = "size")
    ProductVariantMapper toProductVariantMapper(ProductVariant productVariant);

    // Chuyển đổi từ List<Cart> thành List<CartMapper>
    List<CartMapper> toCartMapperList(List<Cart> carts);

    // Chuyển đổi từ List<CartItem> thành List<CartItemMapper>
    List<CartItemMapper> toCartItemMapperList(List<CartItem> cartItems);

    // Chuyển đổi từ List<Product> thành List<ProductMapper>
    List<ProductMapper> toProductMapperList(List<Product> products);

    // Chuyển đổi từ List<ProductVariant> thành List<ProductVariantMapper>
    List<ProductVariantMapper> toProductVariantMapperList(List<ProductVariant> productVariants);
}

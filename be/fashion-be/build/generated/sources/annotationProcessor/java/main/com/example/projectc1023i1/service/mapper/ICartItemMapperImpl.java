package com.example.projectc1023i1.service.mapper;

import com.example.projectc1023i1.Dto.get_data.cart_mapstruck.CartItemMapper;
import com.example.projectc1023i1.Dto.get_data.cart_mapstruck.CartMapper;
import com.example.projectc1023i1.Dto.get_data.cart_mapstruck.ProductMapper;
import com.example.projectc1023i1.Dto.get_data.cart_mapstruck.ProductVariantMapper;
import com.example.projectc1023i1.model.Cart;
import com.example.projectc1023i1.model.CartItem;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.ProductVariant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-03T23:21:27+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class ICartItemMapperImpl implements ICartItemMapper {

    @Override
    public CartMapper toCartMapper(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartMapper.CartMapperBuilder cartMapper = CartMapper.builder();

        cartMapper.cartId( cart.getCartId() );
        cartMapper.user( cart.getUser() );
        cartMapper.createdAt( cart.getCreatedAt() );
        cartMapper.items( toCartItemMapperList( cart.getItems() ) );

        return cartMapper.build();
    }

    @Override
    public CartItemMapper toCartItemMapper(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemMapper.CartItemMapperBuilder cartItemMapper = CartItemMapper.builder();

        cartItemMapper.cartItemId( cartItem.getCartItemId() );
        cartItemMapper.productVariant( cartItem.getProductVariant() );
        cartItemMapper.product( cartItem.getProduct() );
        cartItemMapper.quantity( cartItem.getQuantity() );

        return cartItemMapper.build();
    }

    @Override
    public ProductMapper toProductMapper(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductMapper.ProductMapperBuilder productMapper = ProductMapper.builder();

        productMapper.productId( product.getProductId() );
        productMapper.productName( product.getProductName() );
        productMapper.description( product.getDescription() );
        productMapper.createdAt( product.getCreatedAt() );
        productMapper.updatedAt( product.getUpdatedAt() );
        productMapper.isActive( product.getIsActive() );
        productMapper.thumbnail( product.getThumbnail() );
        productMapper.quality( product.getQuality() );
        productMapper.sellPrice( product.getSellPrice() );
        productMapper.price( product.getPrice() );

        return productMapper.build();
    }

    @Override
    public ProductVariantMapper toProductVariantMapper(ProductVariant productVariant) {
        if ( productVariant == null ) {
            return null;
        }

        ProductVariantMapper.ProductVariantMapperBuilder productVariantMapper = ProductVariantMapper.builder();

        productVariantMapper.productVariantId( productVariant.getProductVariantId() );
        productVariantMapper.sku( productVariant.getSku() );
        productVariantMapper.price( productVariant.getPrice() );
        productVariantMapper.stock( productVariant.getStock() );
        productVariantMapper.product( toProductMapper( productVariant.getProduct() ) );
        productVariantMapper.color( productVariant.getColor() );
        productVariantMapper.size( productVariant.getSize() );

        return productVariantMapper.build();
    }

    @Override
    public List<CartMapper> toCartMapperList(List<Cart> carts) {
        if ( carts == null ) {
            return null;
        }

        List<CartMapper> list = new ArrayList<CartMapper>( carts.size() );
        for ( Cart cart : carts ) {
            list.add( toCartMapper( cart ) );
        }

        return list;
    }

    @Override
    public List<CartItemMapper> toCartItemMapperList(List<CartItem> cartItems) {
        if ( cartItems == null ) {
            return null;
        }

        List<CartItemMapper> list = new ArrayList<CartItemMapper>( cartItems.size() );
        for ( CartItem cartItem : cartItems ) {
            list.add( toCartItemMapper( cartItem ) );
        }

        return list;
    }

    @Override
    public List<ProductMapper> toProductMapperList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductMapper> list = new ArrayList<ProductMapper>( products.size() );
        for ( Product product : products ) {
            list.add( toProductMapper( product ) );
        }

        return list;
    }

    @Override
    public List<ProductVariantMapper> toProductVariantMapperList(List<ProductVariant> productVariants) {
        if ( productVariants == null ) {
            return null;
        }

        List<ProductVariantMapper> list = new ArrayList<ProductVariantMapper>( productVariants.size() );
        for ( ProductVariant productVariant : productVariants ) {
            list.add( toProductVariantMapper( productVariant ) );
        }

        return list;
    }
}

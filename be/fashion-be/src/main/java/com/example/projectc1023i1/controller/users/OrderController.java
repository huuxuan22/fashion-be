package com.example.projectc1023i1.controller.users;

import com.example.projectc1023i1.Dto.OrderDetailDTO;
import com.example.projectc1023i1.Dto.ProductDetailDTO;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.Users;
import com.example.projectc1023i1.request.CartRequest;
import com.example.projectc1023i1.service.OrderService;
import com.example.projectc1023i1.service.impl.ICartService;
import com.example.projectc1023i1.service.impl.IOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICartService cartService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal Users users,
                                         @RequestBody OrderDetailDTO orderDetailDTO,
                                         @RequestBody List<ProductDetailDTO> productDetailDTO) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart/get_all_product")
    public ResponseEntity<?> getAllProductInCart(@AuthenticationPrincipal Users users) {

        return ResponseEntity.ok().build();
    }

    /**
     * Thêm sản phẩm chi tiết vào trong Cart
     * @param users
     * @param productDetailDTO
     * @return
     */
    @PostMapping("/cart/add-to-cart")
    public ResponseEntity<?> addProductVariantToCart(@AuthenticationPrincipal Users users,
                                              @RequestBody ProductDetailDTO productDetailDTO) {
        cartService.addProductVariantToCart(users, productDetailDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Thêm sản phẩm chi tiết vào trong Cart
     * @param users
     * @param product
     * @return
     */
    @PostMapping("/cart/add-cart")
    public ResponseEntity<?> addProductToCart(@AuthenticationPrincipal Users users,
                                              @RequestBody Product product) {
        cartService.addProductToCart(users, product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart/get-all-item")
    public ResponseEntity<?> getAllItemInCart(@AuthenticationPrincipal Users users) {
        return ResponseEntity.ok(cartService.findByUserId(Long.valueOf(users.getUserId())));
    }

    @PostMapping("/cart")
    public ResponseEntity<?> deleteCart(@AuthenticationPrincipal Users users,
                                        @RequestBody List<Long> cartId) {
        for (Long i : cartId) {
            cartService.deleteByCart(i);
        }
        return ResponseEntity.ok("");
    }
}
package com.example.projectc1023i1.Dto.get_data.cart_mapstruck;

import com.example.projectc1023i1.model.CartItem;
import com.example.projectc1023i1.model.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartMapper {
    private Integer cartId;
    private Users user;
    private LocalDateTime createdAt;
    private List<CartItemMapper> items ;
}

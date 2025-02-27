package com.example.projectc1023i1.controller.admin;

import com.example.projectc1023i1.Dto.FindPVariantDTO;
import com.example.projectc1023i1.model.Color;
import com.example.projectc1023i1.model.ProductVariant;
import com.example.projectc1023i1.model.Users;
import com.example.projectc1023i1.respone.errorsValidate.FindPVariantErrors;
import com.example.projectc1023i1.service.impl.IProductVariantService;
import jakarta.validation.Valid;
import lombok.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product-variant/")
public class ProductVariantController {
    @Autowired
    private IProductVariantService productVariantService;

    /**
     * laasy doi tuong product Variant len de cap nhat
     * @param user
     * @param findPVariantDTO
     * @param bindingResult
     * @return
     */
    @GetMapping("get-product-variant")
    public ResponseEntity<?> getProductVariant(@AuthenticationPrincipal Users user,
                                               @Valid @RequestBody FindPVariantDTO findPVariantDTO,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FindPVariantErrors errors = new FindPVariantErrors();
            bindingResult.getFieldErrors().stream()
                    .forEach(fieldError -> {
                        String field = fieldError.getField();
                        String message = fieldError.getDefaultMessage();
                        switch (field) {
                            case "productId":
                                errors.setProductId(message);
                                break;
                            case "sizeId":
                                errors.setSizeId(message);
                                break;
                            case "colorId":
                                errors.setColorId(message);
                                break;
                            default:
                        }
                    });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        ProductVariant productVariant = productVariantService.findByProductIdAndColorIdAndSizeId(
                findPVariantDTO.getProductId(),
                findPVariantDTO.getSizeId(),
                findPVariantDTO.getColorId()
        );
        return ResponseEntity.status(HttpStatus.OK).body(productVariant);
    }

    @GetMapping("get-color")
    public ResponseEntity<?> getColorByP_idAndSizeId(@AuthenticationPrincipal Users users,
                                                     @RequestParam Integer productId,
                                                     @RequestParam Integer sizeId) {
        List<Color> colorList = productVariantService.findByProductIdAndSizeId(productId, sizeId);
        return ResponseEntity.status(HttpStatus.OK).body(colorList);
    }

}

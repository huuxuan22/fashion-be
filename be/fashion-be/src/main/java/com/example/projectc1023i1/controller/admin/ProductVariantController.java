package com.example.projectc1023i1.controller.admin;

import com.example.projectc1023i1.Dto.FindPVariantDTO;
import com.example.projectc1023i1.Dto.ProductVariantDTO;
import com.example.projectc1023i1.Validation.product.NotExistColor;
import com.example.projectc1023i1.Validation.product.NotExistProduct;
import com.example.projectc1023i1.Validation.product.NotExistSize;
import com.example.projectc1023i1.model.Color;
import com.example.projectc1023i1.model.ProductVariant;
import com.example.projectc1023i1.model.Users;
import com.example.projectc1023i1.respone.errorsValidate.FindPVariantErrors;
import com.example.projectc1023i1.respone.errorsValidate.ProductVariantErrors;
import com.example.projectc1023i1.service.impl.IProductVariantService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping("/admin/p-variant/")
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

//    private Integer productVariantId;
//    @NotNull(message = "Bạn chưa chọn giá cho sản phẩm")
//    @Min(value = 0,message = "Giá không được nhỏ hơn 0")
//    private Double price;
//    @NotNull(message = "Bạn chưa nhập số lượng cho sản phẩm")
//    private Integer stock;
//    @NotExistProduct
//    @NotNull(message = "Bạn chưa chọn sản phẩm")
//    private Integer product;
//    @NotExistSize
//    @NotNull(message = "Bạn chưa chọn màu sắc")
//    private Integer color;
//    @NotExistColor
//    @NotNull(message = "Bạn chưa chọn kích thước")
//    private Integer size;


    /**
     * casicái này dùng để cập nhật product variant
     * @param users
     * @param productVariantDTO
     * @param bindingResult
     * @return
     */
    @PatchMapping("update")
//    http://localhost:8080/admin/product-variant/update-product-variant
    public ResponseEntity<?> updateProductVariant(@AuthenticationPrincipal Users users,
                                                  @Valid @RequestBody ProductVariantDTO productVariantDTO,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ProductVariantErrors errors = new ProductVariantErrors();
            bindingResult.getFieldErrors().stream()
                    .forEach(fieldError -> {
                        String field = fieldError.getField();
                        String message = fieldError.getDefaultMessage();
                        switch (field) {
                            case "productVariantId":
                                errors.setProductVariantId(message);
                                break;
                            case "price":
                                errors.setPrice(message);
                                break;
                            case "stock":
                                errors.setStock(message);
                                break;
                            case "productId":
                                errors.setProductId(message);
                                break;
                            case "colorId":
                                errors.setColorId(message);
                                break;
                            case "sizeId":
                                errors.setSizeId(message);
                                break;
                        }
                    });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        ProductVariant productVariant =  productVariantService.UpdateProductVariant(productVariantDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Cập nhật thành công product variant");
    }

    /**
     * Xóa chi tiết sản phẩm này là xóa luôn
     * @param users
     * @param value
     * @return
     */
    @DeleteMapping("delete-p-variant/{value}")
    public ResponseEntity<?> deleteProductVariant(@AuthenticationPrincipal Users users,
                                                  @PathVariable("value") Integer value) {
        productVariantService.deleteProductVariant(value);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}

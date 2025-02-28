package com.example.projectc1023i1.controller.admin;

import com.example.projectc1023i1.Dto.ImageDTO;
import com.example.projectc1023i1.Dto.ProductDTO;
import com.example.projectc1023i1.Dto.ProductMorphology;
import com.example.projectc1023i1.Dto.ProductUpateDTO;
import com.example.projectc1023i1.model.Image;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.Users;
import com.example.projectc1023i1.request.ImageDelete;
import com.example.projectc1023i1.respone.errorsValidate.ImageErrorsRespone;
import com.example.projectc1023i1.respone.errorsValidate.ProductErrorsRespone;
import com.example.projectc1023i1.respone.errorsValidate.ProductMorphologyErrors;
import com.example.projectc1023i1.respone.errorsValidate.ProductUpdateErrorsRespone;
import com.example.projectc1023i1.service.ProductService;
import com.example.projectc1023i1.service.impl.IImageService;
import com.example.projectc1023i1.service.impl.IProductService;
import com.example.projectc1023i1.service.impl.IProductVariantService;
import com.example.projectc1023i1.utils.ProductUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/product/")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private View error;
    @Autowired
    private ProductUtils  productUtils;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IProductVariantService productVariantService;

    /**
     * Lấy tất cả sản phẩm
     * @param user
     * @param size
     * @param page
     * @return
     */
    @GetMapping("get-all-product")
    public ResponseEntity<?> getAllProduct(@AuthenticationPrincipal Users user
                                            , @RequestParam("size") int size
                                            , @RequestParam("page") int page) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng đang bị null hoặc chưa đăng nhập");
        }
        if (size <= 0 || page <= 0) {
            size = 10;
            page = 0;
        }
        Pageable pageable =  PageRequest.of(page,size);
        Page<Product> page1 = productService.getAllProducts( pageable);
        return ResponseEntity.ok(page1);
    }

    /**
     * Thêm mới 1 sản phẩm
     * @param user
     * @param productDTO
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "add-product")
//    http://localhost:8080/admin/product/add-product
    public ResponseEntity<?> addProduct( @AuthenticationPrincipal Users user,
                                         @Valid @RequestBody ProductDTO productDTO,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ProductErrorsRespone productErrorsRespone = new ProductErrorsRespone();
            bindingResult.getFieldErrors().stream()
                    .forEach(error -> {
                        String field = error.getField();
                        String message = error.getDefaultMessage();
                        switch (field) {
                            case "productName":
                                productErrorsRespone.setProductName(message);
                                break;
                            case "description":
                                productErrorsRespone.setDescription(message);
                                break;
                            case "categories":
                                productErrorsRespone.setCategories(message);
                                break;

                            default:
                                break;
                        }
                    });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productErrorsRespone);
        }
        Product product = productService.addProduct(productDTO);
        return ResponseEntity.ok("Add product successfully");
    }

    /**
     * lưu aảnh vào máy và lưu đường dẫn vào trong database
     * @param users
     * @param imageDTO
     * @param bindingResult
     * @return
     * @throws IOException
     */
//    http://localhost:8080/admin/product/upload-image  POST
    @PostMapping( value = "upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(
                @AuthenticationPrincipal Users users,
                @Valid @ModelAttribute ImageDTO imageDTO,
                BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            ImageErrorsRespone errorsRespone = new ImageErrorsRespone();
            StringBuilder morphologyErrors = new StringBuilder();
            bindingResult.getFieldErrors().stream()
            .forEach(error -> {
                String field = error.getField();
                String message = error.getDefaultMessage();
                switch (field) {
                    case "productId":
                        errorsRespone.setProductId(message);
                        break;
                    case "listImage":
                        errorsRespone.setListImage(message);
                        break;
                    default:
                        break;
                }
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsRespone);
        }
        for (MultipartFile file : imageDTO.getListImage()) {
            if (file.getSize() == 0) {
                continue;
            }
            if (file.getSize() > 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("anh qua lon, lon hon 10Byte");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.contains("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("unsupported image type");
            }
            String fileName = productUtils.storeFile(file);
            productService.uploadImage(imageDTO.getProductId(), fileName);
        }
        return null;
    }

    /**
     * HIển thị hình ảnh
     * @param imageName
     * @return
     */
    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> getImage(@PathVariable String imageName) {
        try {
            Path imagePath = Paths.get("uploads/product/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }else {
                return ResponseEntity.notFound().build();
            }
        }catch ( Exception e ) {
                return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lấy tất cả hiình ảnh ra hiển thị
     * @param users
     * @param productId
     * @param request
     * @return
     */
    @GetMapping("get-all-image")
    public ResponseEntity<?> getAllImage(@AuthenticationPrincipal Users users,
                                         @RequestParam Integer productId,
                                         HttpServletRequest request) {
        if (productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<String> imageNames = imageService.findByProductId(productId);
        List<String> imageUrl = imageNames.stream()
                .map(name -> "http://localhost:8080/admin/product/images/"+name)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(imageUrl);
    }

    /**
     *Thêm mới danh sách productVariant thêm theo kiểu dạng như
     * 1 product 1 size và nhiều màu
     * @param users
     * @param productMorphologies
     * @param bindingResult
     * @return
     */
    @PostMapping("add-product-variant")
    public ResponseEntity<?> addProductVariant(@AuthenticationPrincipal Users users,
                                               @RequestBody @Valid List<ProductMorphology> productMorphologies,
                                               BindingResult bindingResult) {
        if (productMorphologies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bạn chưa nhập danh sách ProductVariant");
        }
        ProductMorphologyErrors errors = new ProductMorphologyErrors();
        for (ProductMorphology morphology : productMorphologies) {
            if (bindingResult.hasErrors()) {
                for (FieldError error : bindingResult.getFieldErrors()) {
                    String field = error.getField();
                    String message = error.getDefaultMessage();

                    switch (field) {
                        case "productId":
                            errors.setProductId((errors.getProductId() == null ? "" : errors.getProductId() + " | ") + message);
                            break;
                        case "colorId":
                            errors.setColorId((errors.getColorId() == null ? "" : errors.getColorId() + " | ") + message);
                            break;
                        case "sizeId":
                            errors.setSizeId((errors.getSizeId() == null ? "" : errors.getSizeId() + " | ") + message);
                            break;
                        case "stock":
                            errors.setStock((errors.getStock() == null ? "" : errors.getStock() + " | ") + message);
                            break;
                        case "price":
                            errors.setPrice((errors.getPrice() == null ? "" : errors.getPrice() + " | ") + message);
                            break;
                    }
                }
            }
        }
        // Nếu có bất kỳ lỗi nào thì trả về response lỗi
        if (errors.getColorId() != null || errors.getSizeId() != null || errors.getStock() != null || errors.getPrice() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        for (ProductMorphology productMorphology : productMorphologies) {
            productVariantService.addProductVariant(productMorphology);
        }
        return ResponseEntity.ok("Thêm thành công");
    }

    /**
     * lay thong tin cua product len de update hoac tiep tuc update ben productVariant
     * @param users
     * @param productId
     * @return tra ve doi tuong cua product
     *
     */
    @GetMapping("get-product-by-id")
    public ResponseEntity<?> updateProduct(@AuthenticationPrincipal Users users,
                                           @RequestParam Integer productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    /**
     * chi cap nhat product khi nguoi dung chi muon cap nhat product rieng
     * con khong se vao cap nhat product variant
     * @param productDTO
     * @return
     */
    @PostMapping("update-product")
    public ResponseEntity<?> updateProduct( @AuthenticationPrincipal Users users,
                                            @Valid @RequestBody ProductUpateDTO productDTO,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ProductUpdateErrorsRespone errors = new ProductUpdateErrorsRespone();
            bindingResult.getFieldErrors().stream()
                    .forEach(fieldError -> {
                        String field = fieldError.getField();
                        String message = fieldError.getDefaultMessage();
                        switch (field) {
                            case "productId":
                                errors.setProductId(message);
                            case "productName":
                                errors.setProductName(message);
                                break;
                            case "description":
                                errors.setDescription(message);
                                break;
                            case "categories":
                                errors.setCategories(message);
                                break;
                        }
                    });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        productService.updateProduct(productDTO);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("delete-product/{value}")
    public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal Users users,
                                            @PathVariable("value") Integer value) {
        productService.deleteProduct(value);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete-image")
    public ResponseEntity<?> deleteImage(@AuthenticationPrincipal Users users,
                                         @RequestBody ImageDelete imageDelete) {

        imageService.deleteImage(imageDelete.getProductId(), imageDelete.getImageUrls());
        return ResponseEntity.ok().build();
    }

    @PostMapping("set-main-Image")
    public ResponseEntity<?> setMainImage(@AuthenticationPrincipal Users users,
                                          @RequestParam Integer productId,
                                          @RequestParam String imageUrl) {
        productService.setMainImage(productId, imageUrl);
        return  ResponseEntity.ok().build();
    }

}

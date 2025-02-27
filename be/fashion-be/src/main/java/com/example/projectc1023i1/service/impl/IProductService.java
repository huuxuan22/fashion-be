package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.Dto.ImageDTO;
import com.example.projectc1023i1.Dto.ProductDTO;
import com.example.projectc1023i1.Dto.ProductUpateDTO;
import com.example.projectc1023i1.model.Image;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    Page<Product> getAllProducts(Pageable pageable);
    Product getProductById(Integer id);
    Product addProduct(ProductDTO productDTO);
    Product updateProduct(ProductUpateDTO productUpateDTO);
    void deleteProduct(Integer id);
    Product convertProductDtoToProduct(ProductDTO productDTO);
    void uploadImage(Integer productId, String url);
    void chooseMainPhoto(String url, Integer productId);

}

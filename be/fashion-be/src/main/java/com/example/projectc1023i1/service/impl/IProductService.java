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

import java.io.IOException;
import java.util.List;

public interface IProductService {
    Page<Product> getAllProducts(Pageable pageable,String search,Integer categoryId);
    Product getProductById(Integer id);
    Product addProduct(ProductDTO productDTO,List<MultipartFile> listFile) throws IOException;
    Product updateProduct(ProductUpateDTO productUpateDTO);
    void deleteProduct(Integer id);
    Product convertProductDtoToProduct(ProductDTO productDTO);
    void uploadImage(Integer productId, String url);
    void chooseMainPhoto(String url, Integer productId);
    void setMainImage(Integer productId,String url);
    Page<Product> getAllProductById(Integer productId, Pageable pageable);
    List<String>  findAllProductByValue(String value);
    Long countProduct();
    List<Product> getDiscountProduct();
}

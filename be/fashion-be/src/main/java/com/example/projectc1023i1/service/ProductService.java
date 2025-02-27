package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.ImageDTO;
import com.example.projectc1023i1.Dto.ProductDTO;
import com.example.projectc1023i1.Dto.ProductUpateDTO;
import com.example.projectc1023i1.Exception.DataNotFoundException;
import com.example.projectc1023i1.Exception.HandlerRuntimeException;
import com.example.projectc1023i1.Exception.PayloadTooLargeException;
import com.example.projectc1023i1.Exception.UnsupportedMediaTypeException;
import com.example.projectc1023i1.model.Categories;
import com.example.projectc1023i1.model.Image;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.ProductVariant;
import com.example.projectc1023i1.repository.impl.*;
import com.example.projectc1023i1.service.impl.IProductService;
import com.example.projectc1023i1.service.impl.ISizeService;
import com.example.projectc1023i1.utils.ProductUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepo productRepo;
    @Autowired
    private ICategoriesRepo categoriesRepo;
    @Autowired
    private ISizeRepo sizeRepo;
    @Autowired
    private IProductVariantRepo productVariantRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IColorRepo colorRepo;
    @Autowired
    private ProductUtils productUtils;
    @Autowired
    private IImageRepo imageRepo;

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepo.getAllActiveProduct(pageable);
    }

    @Override
    public Product getProductById(Integer id) {
        Product product = productRepo.findById(id).
                orElseThrow(() -> new DataNotFoundException("Không tìm thấy sản phẩm"));
        return product;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Product addProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        productRepo.findByProductName(productDTO.getProductName())
                .ifPresent(p -> {throw  new DataNotFoundException("Product with name " + productDTO.getProductName() + " already exists");});
        Optional<Categories> categories = categoriesRepo.findById(productDTO.getCategories());
        product.setCategories(categories.get());
        product.setIsActive(true);
        productRepo.save(product);
        return product;
    }




    @Override
    @Transactional
    public Product updateProduct(ProductUpateDTO productDTO) {
        Product productExist = productRepo.findById(productDTO.getProductId()).get();
       modelMapper.map(productDTO, productExist);
        return productRepo.save(productExist);
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepo.deleteById(id);
    }

    @Override
    public Product convertProductDtoToProduct(ProductDTO productDTO) {
        return null;
    }

    /**
     *
     * @param productId
     * @param url
     */
    @Transactional
    @Override
    public void uploadImage(Integer productId, String url) {
        imageRepo.save(Image.builder()
                        .imageUrl(url)
                        .product(productRepo.findById(productId).get())
                .build());
    }

    @Override
    public void chooseMainPhoto(String url, Integer productId) {
            
    }
}

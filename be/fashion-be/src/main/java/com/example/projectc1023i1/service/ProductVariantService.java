package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.AddQualityProduct;
import com.example.projectc1023i1.Dto.ProductMorphology;
import com.example.projectc1023i1.Dto.ProductVariantDTO;
import com.example.projectc1023i1.Dto.get_data.CountByQualitySize;
import com.example.projectc1023i1.Exception.DataNotFoundException;
import com.example.projectc1023i1.model.*;
import com.example.projectc1023i1.repository.impl.IColorRepo;
import com.example.projectc1023i1.repository.impl.IProductRepo;
import com.example.projectc1023i1.repository.impl.IProductVariantRepo;
import com.example.projectc1023i1.repository.impl.ISizeRepo;
import com.example.projectc1023i1.service.impl.IProductService;
import com.example.projectc1023i1.service.impl.IProductVariantService;
import com.example.projectc1023i1.service.impl.ISizeService;
import com.example.projectc1023i1.utils.ProductUtils;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ProductVariantService implements IProductVariantService {
    @Autowired
    private IProductVariantRepo productVariantRepo;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private ProductUtils productUtils;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ISizeRepo sizeRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private IProductRepo productRepo;
    @Autowired
    private IColorRepo colorRepo;

    @Override
    public List<ProductVariant> getAllProductVariants() {
        return productVariantRepo.findAll();
    }

    @Override
    public ProductVariant getProductVariant(Integer id) {
        return productVariantRepo.findById(id).get();
    }

//    public String getSkuFromProductDTO(Integer categoryId,
//                                       Integer colorId,
//                                       Integer sizeId) {
//        Color color = colorRepo.findById(colorId).get();
//        Size size = sizeRepo.findById(sizeId).get();
//        Categories categories = categoriesRepo.findById(categoryId).get();
//        return categories.getCategoriesCode() + "-" +
//                color.getColorName().toUpperCase() + "-" +
//                size.getNameSize().toUpperCase() + "-" + LocalDateTime.now();
//    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ProductVariant addProductVariant(ProductMorphology productMorphology) {
        ProductVariant productVariant = modelMapper.map(productMorphology, ProductVariant.class);
        Product product = productRepo.findById(productMorphology.getProductId()).get();
        return productVariantRepo.save(productVariant);
    }

    @Override
    public ProductVariant updateProductVariant(ProductVariant productVariant) {
        return productVariantRepo.save(productVariant);
    }

    @Override
    public List<Color> findByProductIdAndSizeId(Integer productId, Integer sizeId) {
        productRepo.findById(productId).orElseThrow(() -> new DataNotFoundException("Không tìm thấy sản phẩm "));
        sizeRepo.findById(sizeId).orElseThrow(() -> new DataNotFoundException("Không tìm thấy kích thước"));
        return productVariantRepo.findByProducIdAndSizeId(productId, sizeId);
    }

    @Override
    public ProductVariant findByProductIdAndColorIdAndSizeId(Integer productId, Integer sizeId, Integer colorId) {
        System.out.println("hello");
        return productVariantRepo.
                findQualytyByProductNameAndSizeIdAndColorId(productId,sizeId,colorId);
    }

    @Override
    public void deleteProductVariant(Integer id) {
        productVariantRepo.findById(id).orElseThrow(() -> new DataNotFoundException("Không tìm thấy chi tiết sản phẩm"));
        productVariantRepo.deleteById(id);
    }

    @Override
    public ProductVariant UpdateProductVariant(ProductVariantDTO productVariantDTO) {
        ProductVariant productVariantExist = new ProductVariant();
        productVariantExist.setColor(colorRepo.findById(productVariantDTO.getColorId()).get());
        productVariantExist.setSize(sizeRepo.findById(productVariantDTO.getSizeId()).get());
        productVariantExist.setProduct(productRepo.findById(productVariantDTO.getProductId()).get());
        productVariantExist.setProductVariantId(productVariantDTO.getProductVariantId());
        productVariantExist.setPrice(productVariantDTO.getPrice());
        productVariantExist.setStock(productVariantDTO.getStock());
        return productVariantRepo.save(productVariantExist);
    }

    @Override
    public Page<ProductVariant> findByProductId(Integer productId, Pageable pageable) {
        Page<ProductVariant> productVariants = productVariantRepo.findByProductId(productId, pageable);
        return productVariants;
    }

    @Override
    public Integer getSoldOfProduct(Integer productId) {
        return productVariantRepo.getSoldOfProduct(productId);
    }

    @Override
    public List<CountByQualitySize> countQuanlityWithSizeByColorId(Integer productId, Integer colorId) {
        return productVariantRepo.countQuanlityWithSizeByColorId(productId,colorId);
    }

    @Override
    public void addQuality(List<AddQualityProduct> addQualityProducts, Integer productId) {
        for (AddQualityProduct addQualityProduct : addQualityProducts) {
            Product product = productRepo.findById(productId).get();
            ProductVariant productVariant = productVariantRepo.findQuanlityByProductIdAndSizeIdAndColorId(productId,addQualityProduct.getSizeId(),addQualityProduct.getColorId());
            if (productVariant == null) {
                ProductVariant productVariantNew = ProductVariant.builder()
                        .color(colorRepo.findById(addQualityProduct.getColorId()).get())
                        .size(sizeRepo.findById(addQualityProduct.getSizeId()).get())
                        .product(product)
                        .stock(addQualityProduct.getQuantity())
                        .price(Double.valueOf(product.getPrice()))
                        .sku(productUtils.getSkuFromProductDTO(
                                addQualityProduct.getColorId(),
                                addQualityProduct.getSizeId()
                        ) + "00"+addQualityProduct.getQuantity())
                        .build();
                productVariantRepo.save(productVariantNew);
                product.setQuality(product.getQuality() + addQualityProduct.getQuantity());
                productRepo.save(product);
            }else {
                productVariant.setStock(productVariant.getStock()+addQualityProduct.getQuantity());
                product.setQuality(product.getQuality()+addQualityProduct.getQuantity());
                productRepo.save(product);
                productVariantRepo.save(productVariant);
            }
        }
    }
}

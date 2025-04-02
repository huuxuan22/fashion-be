package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.ListCharacter;
import com.example.projectc1023i1.Dto.ProductDTO;
import com.example.projectc1023i1.Dto.ProductUpateDTO;
import com.example.projectc1023i1.Exception.DataNotFoundException;
import com.example.projectc1023i1.Exception.PayloadTooLargeException;
import com.example.projectc1023i1.Exception.UnsuportedMediaTypeException;
import com.example.projectc1023i1.model.Image;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.ProductVariant;
import com.example.projectc1023i1.repository.impl.*;
import com.example.projectc1023i1.service.impl.IProductService;
import com.example.projectc1023i1.utils.ProductUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private ISubCategories subCategoriesRepo;


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
    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Product addProduct(ProductDTO productDTO) throws IOException {
        Product product = modelMapper.map(productDTO, Product.class);
        List<String> imageListString = new ArrayList<>();
        List<MultipartFile> multipartFiles = productDTO.getThumbnail();
        List<ListCharacter> characters = productDTO.getCharacters();
        for (MultipartFile file : multipartFiles) {
            if (file.getSize() > 1024 * 1024) {
                throw  new PayloadTooLargeException("anh qua lon, lon hon 10Byte");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.contains("image/")) {
                throw  new UnsuportedMediaTypeException("Không hỗ trợ loại ảnh này ");
            }
            String fileName = productUtils.storeFile(file,null);
            imageListString.add(fileName);
        }
        product.setThumbnail(imageListString.get(0));
        product.setQuality(productDTO.getTotalQuantity());
        product.setThumbnail(imageListString.get(0));
        product.setIsActive(true);
        product.setCategories(subCategoriesRepo.findById(Long.valueOf(productDTO.getSubCategories())).get());
        Product productSave = productRepo.save(product);
        for (String image : imageListString) {
            imageRepo.save(Image.builder().product(productSave).imageUrl(image).build());
        }
        List<ProductVariant> productVariantList = new ArrayList<>();
        for (ListCharacter character : characters) {
            productVariantList.add(ProductVariant.builder()
                            .color(colorRepo.findById(character.getColorId()).get())
                            .size(sizeRepo.findById(character.getSizeId()).get())
                            .price(Double.valueOf(character.getQuality()))
                            .product(productSave)
                            .stock((character.getQuality()))
                            .sku(productUtils.getSkuFromProductDTO(
                                    character.getColorId(),
                                    character.getSizeId()
                            ) + "00"+character.getQuality())
                    .build());
        }
        productVariantRepo.saveAll(productVariantList);
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
        if (!productRepo.findById(id).isPresent()) {
            throw new DataNotFoundException("Không tìm thấy sản phẩm");
        }
        productRepo.deleteProduct(id);
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

    @Override
    public void setMainImage(Integer productId, String url) {
        if (!productRepo.findById(productId).isPresent()) {
            throw new DataNotFoundException("Sản phẩm này không tồn tại");
        }
        if (url != null) {
            productRepo.setMainImage(url,productId);
        }
    }

    @Override
    public Page<Product> getAllProductById(Integer productId, Pageable pageable) {
        return null;
    }

    /**
     * cái này là tìm kiếm tên sản phẩm dựa theo từ khóa rồi trả về danh sách tên của sản phẩm
     * @param value
     * @return
     */
    @Override
    public List<String> findAllProductByValue(String value) {
        return productRepo.searchProducts(value);
    }
}

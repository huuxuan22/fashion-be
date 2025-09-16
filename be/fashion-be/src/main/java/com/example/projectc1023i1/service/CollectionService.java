package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.CollectionDTO;
import com.example.projectc1023i1.Dto.get_data.collection_maptruck.CollectionMaptruck;
import com.example.projectc1023i1.Exception.IOException;
import com.example.projectc1023i1.Exception.PayloadTooLargeException;
import com.example.projectc1023i1.Exception.UnsuportedMediaTypeException;
import com.example.projectc1023i1.model.Collection;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.ProductCollection;
import com.example.projectc1023i1.repository.impl.ICollectionRepo;
import com.example.projectc1023i1.repository.impl.IProductRepo;
import com.example.projectc1023i1.service.impl.ICollectionService;
import com.example.projectc1023i1.service.mapper.ICollectionMastruck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CollectionService implements ICollectionService {
    @Autowired
    private ICollectionRepo collectionRepo;

    @Autowired
    private ICollectionMastruck collectionMastruck;
    @Autowired
    private ProductService productService;
    @Autowired
    private IProductRepo productRepo;

    @Override
    public void save(CollectionDTO collectionDTO) throws java.io.IOException {
        String filename = "";
        if (collectionDTO.getImageUrl() != null) {
            filename = storeFile(collectionDTO.getImageUrl());
            MultipartFile file = collectionDTO.getImageUrl();
            if (file.getSize() > 1024 * 1024) {
                throw  new PayloadTooLargeException("anh qua lon, lon hon 10Byte");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.contains("image/")) {
                throw  new UnsuportedMediaTypeException("Không hỗ trợ loại ảnh này ");
            }
        }
        filename = storeFile(collectionDTO.getImageUrl());
        Collection collection = Collection.builder()
                .name(collectionDTO.getName())
                .description(collectionDTO.getDescription())
                .imageUrl(filename)
                .isActive(collectionDTO.getIsActive() != null ? collectionDTO.getIsActive() : true )
                .endDate(collectionDTO.getEndDate()!= null ? collectionDTO.getEndDate() : LocalDateTime.now())
                .startDate(collectionDTO.getStartDate() != null? collectionDTO.getStartDate() : LocalDateTime.now())

                .build();
        List<ProductCollection> productCollectionList = new ArrayList<>();
        for (Product product : collectionDTO.getProduct()) {
           ProductCollection productCollection = new ProductCollection();
           productCollection.setProduct(product);
           productCollection.setCollection(collection);
           productCollectionList.add(productCollection);
        }
        collection.setProductCollections(productCollectionList);
        collectionRepo.save(collection);

    }

    @Override
    public void delete(Integer collectionId) {

    }

    @Override
    public Collection findById(Integer collectionId) {
        return null;
    }

    @Override
    public Collection update(Collection collection) {
        return null;
    }

    @Override
    public List<CollectionMaptruck> findAll() {
        List<Collection> collectionList = collectionRepo.findAll();
        return collectionMastruck.toCollectionMaptruckList(collectionList);
    }

    @Override
    public CollectionMaptruck findLastCollection() {
        return collectionMastruck.converToCollectionMaptruck(collectionRepo.findByStartDate());
    }

    @Override
    public CollectionMaptruck findByLast() {
         return collectionMastruck.converToCollectionMaptruck(collectionRepo.findByLast());
    }

    @Override
    public Page<Collection> findByIsActive(Pageable pageable,String search) {
        return collectionRepo.findByIsActive(pageable,search);
    }

    @Override
    public Integer totalPage(String search) {
        return collectionRepo.countAll(search) % 8 == 0 ? collectionRepo.countAll(search)/8 : collectionRepo.countAll(search)/8 +1;
    }

    @Override
    public void cancelCollection(Collection collection) {
        collection.setIsActive(false);
        collectionRepo.save(collection);
    }

    @Override
    public void restoreCollection(Collection collection) {
        collection.setIsActive(true);
        collectionRepo.save(collection);
    }

    @Override
    public void addProductToCollection(Integer collectionId, List<Integer> productId) {
        Collection collection = collectionRepo.findById(Long.valueOf(collectionId)).get();
        List<ProductCollection> productCollectionList = new ArrayList<>();
        for (int i = collection.getProductCollections().size(); i < productId.size(); i++) {
            productCollectionList.add(ProductCollection.builder()
                            .collection(collection)
                            .product(productRepo.findById(productId.get(i)).get())
                    .build());
        }
        collection.setProductCollections(productCollectionList);
        collectionRepo.save(collection);
    }

    public String storeFile(MultipartFile file) throws IOException, java.io.IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new com.example.projectc1023i1.Exception.IOException("Ảnh bạn chọn không phù hợp ");
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFileName = UUID.randomUUID().toString() + "-" + fileName;
        Path uploadDir = Paths.get("uploads/collection");
        if (!Files.exists(uploadDir)) {
            Files.createDirectory(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    public static boolean isImageFile(MultipartFile file) {
        if (file == null || file.getContentType() == null) {
            return false;
        }
        String contentType = file.getContentType().toLowerCase();
        return contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif") ||
                contentType.equals("image/jpg");
    }
}

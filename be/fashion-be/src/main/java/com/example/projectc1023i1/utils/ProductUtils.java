package com.example.projectc1023i1.utils;

import com.example.projectc1023i1.Dto.ProductDTO;
import com.example.projectc1023i1.Exception.IOException;
import com.example.projectc1023i1.model.Categories;
import com.example.projectc1023i1.model.Color;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.Size;
import com.example.projectc1023i1.repository.impl.ICategoriesRepo;
import com.example.projectc1023i1.repository.impl.IColorRepo;
import com.example.projectc1023i1.repository.impl.IProductRepo;
import com.example.projectc1023i1.repository.impl.ISizeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Component
public class ProductUtils {
    @Autowired
    private IColorRepo colorRepo;

    @Autowired
    private ISizeRepo sizeRepo;

    @Autowired
    private IProductRepo productRepo;

    @Autowired
    private ICategoriesRepo categoriesRepo;

    public String getSkuFromProductDTO(
                                              Integer colorId,
                                              Integer sizeId) {
        Color color = colorRepo.findById(colorId).get();
        Size size = sizeRepo.findById(sizeId).get();
        return
                color.getColorName().toUpperCase() + "-" +
                 size.getNameSize().toUpperCase() + "-" ;
    }

    public String storeFile(MultipartFile file,String fileNameOld) throws IOException, java.io.IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new com.example.projectc1023i1.Exception.IOException("Ảnh bạn chọn không phù hợp ");
        }
        String uploadDirExist = "uploads/";
        // Xóa ảnh cũ nếu có
        if (fileNameOld != null && !fileNameOld.isEmpty()) {
            Path oldFilePath = Paths.get(uploadDirExist + fileNameOld);
            if (Files.exists(oldFilePath)) {
                Files.delete(oldFilePath);  // Xóa file ảnh cũ
            }
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFileName = UUID.randomUUID().toString() + "-" + fileName;
        Path uploadDir = Paths.get("uploads/product");
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

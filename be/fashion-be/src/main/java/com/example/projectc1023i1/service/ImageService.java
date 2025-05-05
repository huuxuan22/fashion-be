package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Exception.DataNotFoundException;
import com.example.projectc1023i1.model.Image;
import com.example.projectc1023i1.repository.impl.IImageRepo;
import com.example.projectc1023i1.repository.impl.IProductRepo;
import com.example.projectc1023i1.service.impl.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService implements IImageService {
    @Autowired
    private IImageRepo imageRepo;
    @Autowired
    private IProductRepo productRepo;
    @Override
    public List<Image> getAllImagesByProductId(Integer productId) {
        return imageRepo.findAllByProductId(productId);
    }

    @Override
    public Image getImageById(Integer id) {
        return null;
    }

    @Override
    public List<String> findByProductId(Integer productId) {
        productRepo.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found"));
        return imageRepo.findByProductId(productId);
    }

    @Override
    public void deleteImage(Integer productId, List<Integer> imageId) {
        if (imageRepo.existsById(productId)) {
            throw new DataNotFoundException("Product not found");
        }
        List<Image> images = imageRepo.findAllById(imageId);
        if (images.size() != imageId.size()) {
            throw new DataNotFoundException("Không có ảnh tồn tại");
        }
        imageRepo.deleteAll(images);
    }
}

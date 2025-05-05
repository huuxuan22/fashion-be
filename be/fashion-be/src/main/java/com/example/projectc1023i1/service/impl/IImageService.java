package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.model.Image;

import java.util.List;

public interface IImageService {
    List<Image> getAllImagesByProductId(Integer id);
    Image getImageById(Integer id);
    List<String> findByProductId(Integer productId);
    void deleteImage(Integer productId,List<Integer> imageId);
}

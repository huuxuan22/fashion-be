package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.get_data.SizeAndQuality;
import com.example.projectc1023i1.model.Size;
import com.example.projectc1023i1.repository.impl.ISizeRepo;
import com.example.projectc1023i1.service.impl.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SizeService implements ISizeService {
    @Autowired
    private ISizeRepo sizeRepo;
    @Override
    public List<Size> getallSize() {
        return sizeRepo.findAll();
    }

    @Override
    public Size getSizeById(Integer id) {
        return sizeRepo.findById(id).get();
    }

    @Override
    public Size addSize(Size size) {
        return sizeRepo.save(size);
    }

    @Override
    public Size updateSize(Size size) {
        return sizeRepo.save(size);
    }

    @Override
    public Size deleteSize(Integer id) {
        return null;
    }

    @Override
    public List<SizeAndQuality> getAllSizeOfOneProduct(Integer productId) {
        List<Object[]> rawResults = sizeRepo.getAllSizeOfOneProduct(productId);
        List<SizeAndQuality> result = new ArrayList<>();

        for (Object[] row : rawResults) {
            Integer sizeId = (Integer) row[0];
            String nameSize = (String) row[1];
            Integer stock = (Integer) row[2];

            result.add(new SizeAndQuality(sizeId, nameSize, stock));
        }
        return result;
    }
}

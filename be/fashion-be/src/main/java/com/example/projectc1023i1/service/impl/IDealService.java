package com.example.projectc1023i1.service.impl;


import com.example.projectc1023i1.Dto.DealDTO;
import com.example.projectc1023i1.model.Deal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IDealService {
    void save(DealDTO dealDTO) throws IOException;
    Page<Deal> findAll(Pageable pageable);
    void delete(Integer dealdId);
    void updateStatus(Deal deal);
    Integer getAllPageOfDeals();
    List<Deal> findByProduct( Integer productId);
    void decreaseOneDealQuality(List<Deal> deals);
}

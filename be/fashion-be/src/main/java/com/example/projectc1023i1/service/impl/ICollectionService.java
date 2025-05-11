package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.Dto.CollectionDTO;
import com.example.projectc1023i1.Dto.get_data.collection_maptruck.CollectionMaptruck;
import com.example.projectc1023i1.model.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ICollectionService {
    void save(CollectionDTO collectionDTO) throws IOException;
    void delete(Integer collectionId);
    Collection findById(Integer collectionId);
    Collection update(Collection collection);
    List<CollectionMaptruck> findAll();
    CollectionMaptruck findLastCollection();
    CollectionMaptruck findByLast();
}

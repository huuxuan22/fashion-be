package com.example.projectc1023i1.service.mapper;

import com.example.projectc1023i1.Dto.get_data.collection_maptruck.CollectionMaptruck;
import com.example.projectc1023i1.Dto.get_data.collection_maptruck.ProductCollectionMapStruck;
import com.example.projectc1023i1.model.Collection;
import com.example.projectc1023i1.model.ProductCollection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ICollectionMastruck {
    // Ánh xạ từ Collection sang CollectionMaptruck
    @Mapping(target = "collectionId", source = "collectionId")
    @Mapping(target = "productCollections", source = "productCollections", qualifiedByName = "mapProductCollections")
    CollectionMaptruck converToCollectionMaptruck(Collection collection);

    // Thêm phương thức chuyển đổi danh sách
    List<CollectionMaptruck> toCollectionMaptruckList(List<Collection> collections);

    @Named("mapProductCollections")
    default List<ProductCollectionMapStruck> mapProductCollections(List<ProductCollection> productCollections) {
        if (productCollections == null) {
            return null;
        }
        return productCollections.stream()
                .map(this::mapToProductCollectionMapStruck)
                .collect(Collectors.toList());
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "product", source = "product")
    ProductCollectionMapStruck mapToProductCollectionMapStruck(ProductCollection productCollection);
}

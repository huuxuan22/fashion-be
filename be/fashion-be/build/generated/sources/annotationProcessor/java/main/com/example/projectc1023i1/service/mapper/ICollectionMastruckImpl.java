package com.example.projectc1023i1.service.mapper;

import com.example.projectc1023i1.Dto.get_data.collection_maptruck.CollectionMaptruck;
import com.example.projectc1023i1.Dto.get_data.collection_maptruck.ProductCollectionMapStruck;
import com.example.projectc1023i1.model.Collection;
import com.example.projectc1023i1.model.ProductCollection;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-18T17:46:27+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ICollectionMastruckImpl implements ICollectionMastruck {

    @Override
    public CollectionMaptruck converToCollectionMaptruck(Collection collection) {
        if ( collection == null ) {
            return null;
        }

        CollectionMaptruck.CollectionMaptruckBuilder collectionMaptruck = CollectionMaptruck.builder();

        collectionMaptruck.collectionId( collection.getCollectionId() );
        collectionMaptruck.productCollections( mapProductCollections( collection.getProductCollections() ) );
        collectionMaptruck.name( collection.getName() );
        collectionMaptruck.description( collection.getDescription() );
        collectionMaptruck.imageUrl( collection.getImageUrl() );
        collectionMaptruck.startDate( collection.getStartDate() );
        collectionMaptruck.endDate( collection.getEndDate() );
        collectionMaptruck.isActive( collection.getIsActive() );

        return collectionMaptruck.build();
    }

    @Override
    public List<CollectionMaptruck> toCollectionMaptruckList(List<Collection> collections) {
        if ( collections == null ) {
            return null;
        }

        List<CollectionMaptruck> list = new ArrayList<CollectionMaptruck>( collections.size() );
        for ( Collection collection : collections ) {
            list.add( converToCollectionMaptruck( collection ) );
        }

        return list;
    }

    @Override
    public ProductCollectionMapStruck mapToProductCollectionMapStruck(ProductCollection productCollection) {
        if ( productCollection == null ) {
            return null;
        }

        ProductCollectionMapStruck.ProductCollectionMapStruckBuilder productCollectionMapStruck = ProductCollectionMapStruck.builder();

        productCollectionMapStruck.id( productCollection.getId() );
        productCollectionMapStruck.product( productCollection.getProduct() );

        return productCollectionMapStruck.build();
    }
}

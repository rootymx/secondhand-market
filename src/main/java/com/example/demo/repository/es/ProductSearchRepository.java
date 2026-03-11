package com.example.demo.repository.es;

import com.example.demo.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, Long> {
    List<ProductDocument> findByTitleContaining(String title);
    List<ProductDocument> findByCategory(String category);
}

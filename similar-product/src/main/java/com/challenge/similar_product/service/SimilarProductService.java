package com.challenge.similar_product.service;

import com.challenge.similar_product.domain.ProductDetail;
import java.util.List;

public interface SimilarProductService {
    List<ProductDetail> getSimilarProducts(String productId);
}
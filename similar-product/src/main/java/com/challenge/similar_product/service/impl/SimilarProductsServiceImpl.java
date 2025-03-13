package com.challenge.similar_product.service.impl;

import com.challenge.similar_product.client.ProductClient;
import com.challenge.similar_product.client.response.ProductDetailResponse;
import com.challenge.similar_product.controller.exception.InternalServerErrorException;
import com.challenge.similar_product.controller.exception.ProductNotFoundException;
import com.challenge.similar_product.domain.ProductDetail;
import com.challenge.similar_product.service.SimilarProductService;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class SimilarProductsServiceImpl implements SimilarProductService {

    private static final Logger LOG = LoggerFactory.getLogger(SimilarProductService.class);

    private final ProductClient productClient;

    @Autowired
    public SimilarProductsServiceImpl(ProductClient productClient) {
        this.productClient = productClient;
    }

    @Override
    public List<ProductDetail> getSimilarProducts(String productId) {
        try {
            LOG.warn("method=getSimilarProducts message=Fetching similar products for product ID: {}", productId);
            List<String> similarProductIds = productClient.getSimilarProductIds(productId);
            LOG.warn("method=getSimilarProducts message=Similar products found: {}", similarProductIds);

            List<ProductDetail> products = similarProductIds.stream()
                    .map(this::fetchProductDetail)
                    .collect(Collectors.toList());

            LOG.warn("method=getSimilarProducts message=Total similar products returned: {}", products.size());
            return products;
        } catch (HttpClientErrorException.NotFound e) {
            LOG.error("method=getSimilarProducts message=Product not found: {}", productId, e);
            throw new ProductNotFoundException("Product not found: " + productId);
        } catch (Exception e) {
            LOG.error("method=getSimilarProducts message=Unexpected error fetching similar products for ID: {}", productId, e);
            throw new InternalServerErrorException("Internal server error occurred while fetching similar products.");
        }

    }
    private ProductDetail fetchProductDetail(String productId) {
        try {
            LOG.warn("method=fetchProductDetail message=Fetching details for product ID: {}", productId);
            ProductDetailResponse response = productClient.getProductDetail(productId);
            return convertToDomain(response);
        } catch (HttpClientErrorException.NotFound e) {
            LOG.error("method=fetchProductDetail message=Product not found: {}", productId, e);
            throw new ProductNotFoundException("Product not found: " + productId);
        } catch (Exception e) {
            LOG.error("method=fetchProductDetail message=Error fetching product details for ID: {}", productId, e);
            throw new InternalServerErrorException("Error fetching product details.");
        }
    }


    private ProductDetail convertToDomain(ProductDetailResponse response) {
        return new ProductDetail.Builder()
                .id(response.id())
                .name(response.name())
                .price(response.price())
                .availability(response.availability())
                .build();
    }


}
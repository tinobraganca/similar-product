package com.challenge.similar_product.service;

import com.challenge.similar_product.client.ProductClient;
import com.challenge.similar_product.client.response.ProductDetailResponse;
import com.challenge.similar_product.controller.exception.InternalServerErrorException;
import com.challenge.similar_product.controller.exception.ProductNotFoundException;
import com.challenge.similar_product.domain.ProductDetail;
import com.challenge.similar_product.service.impl.SimilarProductsServiceImpl;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimilarProductsServiceTest {

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private SimilarProductsServiceImpl similarProductsService;

    @Test
    void testGetSimilarProducts_Success() {
        String productId = "1";
        List<String> similarIds = Arrays.asList("2", "3");
        ProductDetailResponse product2 = new ProductDetailResponse("2", "Product 2", 10.99, true);
        ProductDetailResponse product3 = new ProductDetailResponse("3", "Product 3", 20.99, false);

        when(productClient.getSimilarProductIds(productId)).thenReturn(similarIds);
        when(productClient.getProductDetail("2")).thenReturn(product2);
        when(productClient.getProductDetail("3")).thenReturn(product3);

        List<ProductDetail> result = similarProductsService.getSimilarProducts(productId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Product 2", result.get(0).getName());
        assertEquals("Product 3", result.get(1).getName());
        verify(productClient, times(1)).getSimilarProductIds(any());
        verify(productClient, times(2)).getProductDetail(anyString());
    }

    @Test
    void testGetSimilarProducts_ProductNotFound() {
        String productId = "999";
        when(productClient.getSimilarProductIds(productId)).thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(ProductNotFoundException.class, () -> similarProductsService.getSimilarProducts(productId));
    }

    @Test
    void testFetchProductDetail_ProductNotFound() {
        String productId = "999";
        when(productClient.getSimilarProductIds(productId))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found", null, null, null));

        assertThrows(ProductNotFoundException.class, () -> similarProductsService.getSimilarProducts(productId));
    }

    @Test
    void testGetSimilarProducts_InternalServerError() {
        String productId = "1";
        when(productClient.getSimilarProductIds(productId)).thenThrow(new RuntimeException("Unexpected error"));

        assertThrows(InternalServerErrorException.class, () -> similarProductsService.getSimilarProducts(productId));
    }
}

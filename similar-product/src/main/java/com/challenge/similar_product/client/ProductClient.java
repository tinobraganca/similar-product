package com.challenge.similar_product.client;

import com.challenge.similar_product.client.response.ProductDetailResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "productClient", url = "${feign-integration.similar-api.url}")
public interface ProductClient {
    @GetMapping("/product/{productId}/similarids")
    List<String> getSimilarProductIds(@PathVariable("productId") String productId);

    @GetMapping("/product/{productId}")
    ProductDetailResponse getProductDetail(@PathVariable("productId") String productId);
}
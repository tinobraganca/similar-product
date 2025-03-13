package com.challenge.similar_product.controller;

import com.challenge.similar_product.controller.api.SimilarProductApi;
import com.challenge.similar_product.domain.ProductDetail;
import com.challenge.similar_product.service.SimilarProductService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class SimilarProductsController implements SimilarProductApi {

    private static final Logger LOG = LoggerFactory.getLogger(SimilarProductsController.class);

    private final SimilarProductService similarProductService;

    @Autowired
    public SimilarProductsController(SimilarProductService similarProductService) {
        this.similarProductService = similarProductService;
    }

    @GetMapping("/{productId}/similar")
    @Override
    public List<ProductDetail> getSimilarProducts(@PathVariable String productId) {
        LOG.info("method=getSimilarProducts message=Received request to fetch similar products for ID: {}", productId);
        List<ProductDetail> products = similarProductService.getSimilarProducts(productId);
        LOG.info("method=getSimilarProducts message=Request completed. {} products returned.", products.size());
        return products;

    }
}
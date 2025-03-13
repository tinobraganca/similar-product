package com.challenge.similar_product.controller;

import com.challenge.similar_product.controller.exception.InternalServerErrorException;
import com.challenge.similar_product.controller.exception.ProductNotFoundException;
import com.challenge.similar_product.domain.ProductDetail;
import com.challenge.similar_product.service.SimilarProductService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SimilarProductsControllerTest {

    @Mock
    private SimilarProductService similarProductService;

    @InjectMocks
    private SimilarProductsController similarProductsController;

    private MockMvc mockMvc;

    @Test
    void testGetSimilarProducts_Success() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(similarProductsController).build();

        List<ProductDetail> products = Arrays.asList(
                new ProductDetail.Builder().id("2").name("Product 2").price(10.99).availability(true).build(),
                new ProductDetail.Builder().id("3").name("Product 3").price(20.99).availability(false).build()
        );

        when(similarProductService.getSimilarProducts("1")).thenReturn(products);

        mockMvc.perform(get("/product/1/similar").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product 2"))
                .andExpect(jsonPath("$[1].name").value("Product 3"));

        verify(similarProductService, times(1)).getSimilarProducts(any());
    }

    @Test
    void testGetSimilarProducts_ProductNotFound() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(similarProductsController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        when(similarProductService.getSimilarProducts("999")).thenThrow(new ProductNotFoundException("Product not found: 999"));

        mockMvc.perform(get("/product/999/similar").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found: 999"));
        verify(similarProductService, times(1)).getSimilarProducts(any());
    }

    @Test
    void testGetSimilarProducts_InternalServerError() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(similarProductsController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        when(similarProductService.getSimilarProducts("1")).thenThrow(new InternalServerErrorException("Internal server error occurred."));

        mockMvc.perform(get("/product/1/similar").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal server error occurred."));
        verify(similarProductService, times(1)).getSimilarProducts(any());

    }
}
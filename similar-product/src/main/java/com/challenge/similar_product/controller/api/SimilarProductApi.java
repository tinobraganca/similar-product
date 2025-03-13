package com.challenge.similar_product.controller.api;

import com.challenge.similar_product.domain.ProductDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Similar Products API", description = "API for retrieving similar products")
public interface SimilarProductApi {
    @Operation(description = "Retrieves similar products for a given product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Similar products retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDetail.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    List<ProductDetail> getSimilarProducts(String productId);
}

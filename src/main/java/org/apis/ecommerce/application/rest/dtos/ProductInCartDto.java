package org.apis.ecommerce.application.rest.dtos;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ProductInCartDto {
    long productId;
    String description;
    long quantity;
    double pricePerUnit;
    List<PhotoDTO> photos;
}

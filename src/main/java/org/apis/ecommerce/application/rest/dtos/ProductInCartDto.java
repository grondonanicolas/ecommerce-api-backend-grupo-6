package org.apis.ecommerce.application.rest.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductInCartDto {
    long productId;
    String description;
    long quantity;
    double pricePerUnit;
}

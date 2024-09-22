package org.apis.ecommerce.application.rest.dtos;

import lombok.Value;

@Value
public class CartRequestDto {
    int productId;
    int quantity; 
}

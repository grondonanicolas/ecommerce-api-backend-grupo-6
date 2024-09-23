package org.apis.ecommerce.application.rest.dtos;

import lombok.Value;

@Value
public class AddProductToCartDto {
    int productId;
    int quantity; 
}

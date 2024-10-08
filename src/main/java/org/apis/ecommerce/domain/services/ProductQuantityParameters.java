package org.apis.ecommerce.domain.services;

import lombok.Builder;
import lombok.Value;
import org.apis.ecommerce.domain.models.User;

@Value
@Builder
public class ProductQuantityParameters {
    int productToModifyId;
    int quantity;
    User user;
}
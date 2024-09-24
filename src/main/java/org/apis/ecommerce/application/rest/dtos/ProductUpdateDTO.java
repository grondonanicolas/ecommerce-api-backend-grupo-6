package org.apis.ecommerce.application.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apis.ecommerce.domain.enums.ProductState;

@Setter
@Getter
@AllArgsConstructor
public class ProductUpdateDTO {
    private String description;
    private String name;
    private double price;
    private int stock;
    private Integer categoryId;
    private ProductState state;
}

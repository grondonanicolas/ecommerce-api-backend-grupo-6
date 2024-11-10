package org.apis.ecommerce.application.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apis.ecommerce.domain.models.Category;

@Setter
@Getter
@AllArgsConstructor
public class ProductDTO {
    private Integer id;
    private String description;
    private double price;
    private int stock;
    private String category;
    private String image;
    private String name;
    private String state;
}

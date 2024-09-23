package org.apis.ecommerce.application.rest.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductCreateDTO {
    private Integer id;
    private String description;
    private double price;
    private int stock;
    private Integer categoryId;
}
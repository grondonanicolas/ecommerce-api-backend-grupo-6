package org.apis.ecommerce.application.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductoDTO {
    private Integer id;
    private String description;
    private double precio;
    private int stock;
    // private String productState;
    // private String category;
    // private String user;
}

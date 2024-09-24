package org.apis.ecommerce.application.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricDTO {
    private Integer id;
    private String description;
    private Double price;
    private Integer stock;
    private String category;

    public HistoricDTO(ProductDTO productDTO) {
        this.id = productDTO.getId();
        this.description = productDTO.getDescription();
        this.price = productDTO.getPrice();
        this.stock = productDTO.getStock();
        this.category = productDTO.getCategory();
    }
}
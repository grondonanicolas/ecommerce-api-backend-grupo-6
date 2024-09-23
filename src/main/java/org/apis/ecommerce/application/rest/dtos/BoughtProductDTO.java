package org.apis.ecommerce.application.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apis.ecommerce.domain.models.BoughtProduct;

@Setter
@Getter
@AllArgsConstructor
public class BoughtProductDTO {
    private String description;
    private double pricePerUnit;
    private int quantity;

    public BoughtProductDTO(BoughtProduct entity) {
        this.description = "Missing description";
        this.pricePerUnit = entity.getPricePerUnit();
        this.quantity = entity.getQuantity();
    }
}

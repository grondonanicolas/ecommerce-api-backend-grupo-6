package org.apis.ecommerce.application.rest.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apis.ecommerce.domain.models.BoughtProduct;
import org.apis.ecommerce.domain.models.Photo;
import org.apis.ecommerce.domain.models.Product;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class BoughtProductDTO {
    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "El precio debe ser mayor o igual que 0")
    private double pricePerUnit;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser mayor que 0")
    private int quantity;

    private double subtotal;

    private String image;
    private String name;
    private int productId;

    public BoughtProductDTO(BoughtProduct entity) {
        this.description = entity.getDescription();
        this.pricePerUnit = entity.getPricePerUnit();
        this.quantity = entity.getQuantity();
        this.subtotal = this.quantity * this.pricePerUnit;
        Product p = entity.getProduct();
        if (p != null) {
            this.name = p.getName();
            this.productId = p.getId();
            List<Photo> photos = p.getPhotos();
            if (!photos.isEmpty()) {
                this.image = photos.get(0).getUrl();
            }
        }
    }
}

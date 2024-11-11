package org.apis.ecommerce.application.rest.dtos;

import org.apis.ecommerce.application.rest.dtos.PhotoDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
public class ProductCreateDTO {
    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @NotBlank(message = "El nombre del producto no puede estar vacio")
    private String name;

    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "El precio debe ser mayor o igual que 0")
    private double price;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock debe ser mayor o igual que 0")
    private int stock;

    @NotNull(message = "El ID de la categoría no puede ser nulo")
    @Min(value = 1, message = "El ID de la categoría debe ser mayor que 0")
    private Integer categoryId;

    @NotNull(message = "El producto tiene que tener imagen")
    private List<PhotoDTO> photos;
}
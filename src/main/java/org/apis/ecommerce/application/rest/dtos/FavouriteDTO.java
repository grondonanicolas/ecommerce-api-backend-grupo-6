package org.apis.ecommerce.application.rest.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteDTO {
    private Integer id;
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private String name;
    private List<PhotoDTO> photos;

    public FavouriteDTO(ProductDTO productDTO) {
        this.id = productDTO.getId();
        this.description = productDTO.getDescription();
        this.price = productDTO.getPrice();
        this.stock = productDTO.getStock();
        this.category = productDTO.getCategory();
        this.name = productDTO.getName();
        this.photos = productDTO.getPhotos();
    }
}
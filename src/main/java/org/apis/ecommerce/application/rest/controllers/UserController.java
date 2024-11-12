package org.apis.ecommerce.application.rest.controllers;


import lombok.AllArgsConstructor;
import org.apis.ecommerce.application.rest.dtos.CreateHistoricDTO;
import org.apis.ecommerce.application.rest.dtos.CreateFavouriteDTO;
import org.apis.ecommerce.application.rest.dtos.FavouriteDTO;
import org.apis.ecommerce.application.rest.dtos.HistoricDTO;
import org.apis.ecommerce.application.rest.dtos.ProductDTO;
import org.apis.ecommerce.application.rest.dtos.PhotoDTO;
import org.apis.ecommerce.domain.models.Favourite;
import org.apis.ecommerce.domain.models.Historic;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.models.User;
import org.apis.ecommerce.application.rest.services.IUserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private IUserService userService;

    @PostMapping("/historic")
    public void addProductHistoric(@RequestBody @Valid CreateHistoricDTO historic, @AuthenticationPrincipal User user) throws Exception {
        userService.addProductHistoric(historic.getProductId(), user);
    }

    @GetMapping("/historic")
    public List<HistoricDTO> getHistoricByUser(@AuthenticationPrincipal User user) throws Exception {
        List<Historic> historic = userService.getProductHistoric(user);
        return historic.stream()
                .map(historicItem -> {
                    Product product = historicItem.getProduct();
                    ProductDTO productDTO = new ProductDTO(
                            product.getId(),
                            product.getDescription(),
                            product.getPricePerUnit(),
                            product.getCurrentStock(),
                            product.getCategory() != null ? product.getCategory().getCategory() : null,
                            product.getName(),
                            product.getCurrentState().toString(),
                            product.getPhotos().stream()
                                    .map(photo -> new PhotoDTO(photo.getPriority(), photo.getUrl()))
                                    .collect(Collectors.toList()) // Convertir a lista de PhotoDTOs
                    );

                    return new HistoricDTO(productDTO);
                })
                .toList();
    }

    @GetMapping("/favourite")
    public List<FavouriteDTO> getFavouriteByUser(@AuthenticationPrincipal User user) throws Exception {
        List<Favourite> favourites = userService.getProductFavourites(user);
        return favourites.stream()
                .map(favouriteItem -> {
                    Product product = favouriteItem.getProduct();

                    ProductDTO productDTO = new ProductDTO(
                            product.getId(),
                            product.getDescription(),
                            product.getPricePerUnit(),
                            product.getCurrentStock(),
                            product.getCategory() != null ? product.getCategory().getCategory() : null,
                            product.getName(),
                            product.getCurrentState().toString(),
                            product.getPhotos().stream()
                                    .map(photo -> new PhotoDTO(photo.getPriority(), photo.getUrl()))
                                    .collect(Collectors.toList()) // Convertir a lista de PhotoDTOs
                    );

                    return new FavouriteDTO(productDTO);
                })
                .toList();
    }

    @PostMapping("/favourite")
    public void addProductFavourite(@RequestBody @Valid CreateFavouriteDTO favourite, @AuthenticationPrincipal User user) throws Exception {
        userService.addProductFavourite(favourite.getProductId(), user);
    }

    @DeleteMapping("/favourite")
    public void deleteProductFavourite(@RequestBody @Valid CreateFavouriteDTO favourite, @AuthenticationPrincipal User user) throws Exception {
        userService.deleteProductFavourite(favourite.getProductId(), user);
    }

}

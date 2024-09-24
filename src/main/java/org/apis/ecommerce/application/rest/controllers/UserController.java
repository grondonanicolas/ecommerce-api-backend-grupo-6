package org.apis.ecommerce.application.rest.controllers;


import lombok.AllArgsConstructor;
import org.apis.ecommerce.application.rest.dtos.UserDTO;
import org.apis.ecommerce.application.rest.dtos.FavouriteDTO;
import org.apis.ecommerce.application.rest.dtos.HistoricDTO;
import org.apis.ecommerce.application.rest.dtos.OutstandingDTO;
import org.apis.ecommerce.application.rest.dtos.ProductDTO;
import org.apis.ecommerce.domain.models.Favourite;
import org.apis.ecommerce.domain.models.Historic;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.models.User;
import org.apis.ecommerce.application.rest.services.IUserService;
import org.apis.ecommerce.domain.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws Exception {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/historic")
    public void addProductHistoric(@RequestBody HistoricDTO historic, @AuthenticationPrincipal User user) throws Exception {
        userService.addProductHistoric(historic.getProductDTO().getId(), user);
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
                            product.getCategory() != null ? product.getCategory().getCategory() : null); 
    
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
                        product.getCategory() != null ? product.getCategory().getCategory() : null
                    );
                    
                    return new FavouriteDTO(productDTO); 
                })
                .toList();
    }

    @PostMapping("/historic")
    public void addProductFavourite(@RequestBody FavouriteDTO favourite, @AuthenticationPrincipal User user) throws Exception {
        userService.addProductHistoric(favourite.getProductDTO().getId(), user);
    }

}

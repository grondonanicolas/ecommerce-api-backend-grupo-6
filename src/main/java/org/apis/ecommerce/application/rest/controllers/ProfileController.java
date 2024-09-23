package org.apis.ecommerce.application.rest.controllers;

import org.apis.ecommerce.application.rest.dtos.CheckoutDTO;
import org.apis.ecommerce.application.rest.dtos.ProductDTO;
import org.apis.ecommerce.application.rest.dtos.UserProfileDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping
    public UserProfileDTO getUserProfile() {
        return new UserProfileDTO("Juan", "Pérez", "juan.perez@example.com");
    }

    @GetMapping("/checkouts")
    public CheckoutDTO[] getUserCheckouts() {

        ProductDTO producto1 = new ProductDTO(1, "Camiseta deportiva de algodón con logo Reebok", 2999.99,5);
        ProductDTO producto2 = new ProductDTO(2, "Zapatillas deportivas con tecnología Air Max para mayor confort", 12499.90,2);
        ProductDTO producto3 = new ProductDTO(3, "Shorts de entrenamiento con tejido que absorbe el sudor", 4599.95,3);
        ProductDTO producto4 = new ProductDTO(4, "Sudadera con capucha ideal para entrenamiento y uso casual", 6999.89,4);
        ProductDTO producto5 = new ProductDTO(5, "Pantalón deportivo ajustado con bolsillos laterales", 7499.99,4);
        ProductDTO producto6 = new ProductDTO(6, "Gorra ajustable con visera curva y logo bordado Reebok", 2499.90,2);

        ProductDTO[] productosComprados1 = {
                producto1, producto2, producto3, producto4
        };

        ProductDTO[] productosComprados2 = {
                producto5, producto6
        };

        CheckoutDTO checkout1 = new CheckoutDTO("2024-01-01 12:01:32", productosComprados1);

        CheckoutDTO checkout2 = new CheckoutDTO("2024-05-18 16:38:45", productosComprados2);

        CheckoutDTO[] checkouts = new CheckoutDTO[2];
        checkouts[0] = checkout1;
        checkouts[1] = checkout2;

        return checkouts;
    }
}

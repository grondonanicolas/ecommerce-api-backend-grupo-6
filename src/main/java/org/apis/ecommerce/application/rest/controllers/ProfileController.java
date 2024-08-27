package org.apis.ecommerce.application.rest.controllers;

import org.apis.ecommerce.application.rest.dtos.CheckoutDTO;
import org.apis.ecommerce.application.rest.dtos.ProductoDTO;
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

        ProductoDTO producto1 = new ProductoDTO("Camiseta Reebok Classic", "Camiseta deportiva de algodón con logo Reebok", 2999.99);
        ProductoDTO producto2 = new ProductoDTO("Zapatillas Nike Air Max", "Zapatillas deportivas con tecnología Air Max para mayor confort", 12499.90);
        ProductoDTO producto3 = new ProductoDTO("Shorts Nike Dri-FIT", "Shorts de entrenamiento con tejido que absorbe el sudor", 4599.95);
        ProductoDTO producto4 = new ProductoDTO("Sudadera Reebok Training", "Sudadera con capucha ideal para entrenamiento y uso casual", 6999.89);
        ProductoDTO producto5 = new ProductoDTO("Pantalón Nike Sportswear", "Pantalón deportivo ajustado con bolsillos laterales", 7499.99);
        ProductoDTO producto6 = new ProductoDTO("Gorra Reebok", "Gorra ajustable con visera curva y logo bordado Reebok", 2499.90);

        ProductoDTO[] productosComprados1 = {
                producto1, producto2, producto3, producto4
        };

        ProductoDTO[] productosComprados2 = {
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

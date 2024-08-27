package org.apis.ecommerce.application.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CheckoutDTO {
        private String fecha;
        private ProductoDTO[] productosComprados;
}

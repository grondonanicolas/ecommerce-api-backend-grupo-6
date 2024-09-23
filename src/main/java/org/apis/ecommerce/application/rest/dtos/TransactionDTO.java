package org.apis.ecommerce.application.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apis.ecommerce.domain.models.BoughtProduct;
import org.apis.ecommerce.domain.models.Transaction;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class TransactionDTO {
        private String fecha;
        private List<BoughtProductDTO> productosComprados;

        public TransactionDTO(Transaction entity) {
                this.fecha = entity.getCreatedAt().toString();
                this.productosComprados = new ArrayList<>();
                List<BoughtProduct> productos = entity.getBoughtProducts();
                for (BoughtProduct producto : productos) {
                        BoughtProductDTO productoCompradoDTO = new BoughtProductDTO(producto);
                        this.productosComprados.add(productoCompradoDTO);
                }
        }
}

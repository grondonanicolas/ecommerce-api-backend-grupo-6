package org.apis.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apis.ecommerce.application.rest.dtos.CartDetailDto;
import org.apis.ecommerce.application.rest.dtos.ProductInCartDto;
import org.apis.ecommerce.domain.services.ProductQuantityRequest;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cart {
    // todo: tener en cuenta que hay que crear el carrito cuando se crea el usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;  // Se utiliza para el método cartRepository.findByUser

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductInCart> selectedProducts;

    public void addRequestedProductQuantity(Product requestedProduct, int requestedQuantity) {
        requestedProduct.validateThatItIsActive();
        Optional<ProductInCart> productInCart = getProductInCart(requestedProduct);
        productInCart.ifPresentOrElse(
                existingProduct -> existingProduct.addRequestedQuantity(requestedQuantity), 
                () -> addNewProductWithRequestedQuantity(requestedProduct, requestedQuantity));
    }

    private Optional<ProductInCart> getProductInCart(Product requestedProduct) {
        return selectedProducts.stream()
                .filter(productInCart -> productInCart.isSameProduct(requestedProduct))
                .findFirst();
    }

    private void addNewProductWithRequestedQuantity(Product product, int requestedQuantity) {
        int productId = product.getId();

        ProductInCart productInCart = ProductInCart.builder()
                .id(new ProductInCartId(this.id, productId))
                .cart(this)
                .product(product)
                .currentQuantity(0)
                .build();
        
        productInCart.addRequestedQuantity(requestedQuantity);

        selectedProducts.add(productInCart);
    }

    public void clear() {
        selectedProducts.clear();
    }

    public void removeProduct(int productIdToRemove) {
        ProductInCart productInCartToRemove = findProductInCart(productIdToRemove);
        selectedProducts.remove(productInCartToRemove);
    }

    private ProductInCart findProductInCart(int productToModifyId) {
        return selectedProducts.stream()
                .filter(productInCart -> productInCart.isSameProductId(productToModifyId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("El producto indicado no se encuentra en el carrito"));
    }

    public List<ProductInCart> getSelectedProducts() {
        return selectedProducts.stream().toList();
    }

    public void checkOutProducts() {
        selectedProducts.forEach(ProductInCart::checkOut);
        clear();
    }

    public void validateIsNotEmpty() {
        if (selectedProducts.isEmpty()) {
            throw new IllegalStateException("No hay productos para comprar en el carrito");
        }
    }

    public CartDetailDto toDto() {
        List<ProductInCartDto> productInCartDtos = selectedProducts.stream().map(ProductInCart::toDto).toList();
        
        double cartPriceForDto = productInCartDtos.stream()
                .map(product -> product.getQuantity() * product.getPricePerUnit())
                .reduce(0d, Double::sum);
        
        return CartDetailDto.builder()
                .productsInCart(productInCartDtos)
                .cartPrice(cartPriceForDto)
                .build();
    }

    public void modifyProductQuantity(ProductQuantityRequest productQuantityRequest) {
        int productToModifyId = productQuantityRequest.getProductToModifyId();
        int requestedQuantity = productQuantityRequest.getQuantity();
        
        ProductInCart productInCartToModify = findProductInCart(productToModifyId);
        productInCartToModify.modifyQuantity(requestedQuantity);
    }

}

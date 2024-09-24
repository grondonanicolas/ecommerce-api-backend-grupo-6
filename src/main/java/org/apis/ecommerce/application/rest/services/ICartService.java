package org.apis.ecommerce.application.rest.services;

import org.apis.ecommerce.application.rest.dtos.AddProductToCartDto;
import org.apis.ecommerce.domain.models.Cart;
import org.apis.ecommerce.domain.models.User;
import org.apis.ecommerce.domain.services.ProductQuantityRequest;

public interface ICartService {
    public void addProductToCart(AddProductToCartDto addProductToCartDto, User requestingUser);

    public Cart getUserCart(User requestingUser);
    public void clearUserCart(User requestingUser);
    public void removeProductFromUserCart(int productIdToRemove, User requestingUser);
    public void checkOutUserCart(User requestingUser);
    public void modifyProductQuantity(ProductQuantityRequest productQuantityRequest);
}

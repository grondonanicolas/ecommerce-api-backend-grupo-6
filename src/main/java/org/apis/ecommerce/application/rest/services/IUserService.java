package org.apis.ecommerce.application.rest.services;

import org.apis.ecommerce.application.rest.dtos.UserDTO;
import org.apis.ecommerce.domain.models.Favourite;
import org.apis.ecommerce.domain.models.Historic;
import org.apis.ecommerce.domain.models.User;
import java.util.List;

public interface IUserService {
    public UserDTO getUserById(Integer id) throws Exception;

    public List<Historic> getProductHistoric(User user) throws Exception;

    public List<Favourite> getProductFavourites(User user) throws Exception;

    public void addProductFavourite(Integer productId, User user) throws Exception;

    public void addProductHistoric(Integer productId, User user) throws Exception;

    public void deleteProductFavourite(Integer productId, User user) throws Exception;

}

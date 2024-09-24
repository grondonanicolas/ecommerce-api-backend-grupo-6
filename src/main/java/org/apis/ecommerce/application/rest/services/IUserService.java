package org.apis.ecommerce.application.rest.services;

import org.apis.ecommerce.application.rest.dtos.UserDTO;

public interface IUserService {
    public UserDTO getUserById(Long id) throws Exception;
}

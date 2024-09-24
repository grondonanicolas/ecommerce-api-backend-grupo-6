package org.apis.ecommerce.domain.services;


import org.apis.ecommerce.application.rest.dtos.UserDTO;
import org.apis.ecommerce.application.rest.services.IUserService;
import org.apis.ecommerce.domain.models.User;
import org.apis.ecommerce.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserById(Long id) throws Exception {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("An error has ocurred"));
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}

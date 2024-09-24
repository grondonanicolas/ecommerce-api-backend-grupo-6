package org.apis.ecommerce.domain.services;


import org.apis.ecommerce.application.rest.dtos.UserDTO;
import org.apis.ecommerce.domain.models.Favourite;
import org.apis.ecommerce.domain.models.Historic;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.application.rest.services.IUserService;
import org.apis.ecommerce.domain.models.User;
import org.apis.ecommerce.domain.repositories.IProductRepository;
import org.apis.ecommerce.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IProductRepository productRepository;

    public UserDTO getUserById(Integer id) throws Exception {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("An error has ocurred"));
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    public void addProductHistoric(Integer productId, User user) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        Historic historic = Historic.builder().user(user).product(product).build();

        user.addProductToHistoric(historic);
        userRepository.save(user);
    }

    public void addProductFavourite(Integer productId, User user) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        Favourite favourite = Favourite.builder().user(user).product(product).build();

        user.addProductToFavourite(favourite);
        userRepository.save(user);
    }

    public List<Historic> getProductHistoric(User user){
        user = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("An error has ocurred"));
        return user.getHistoric();
    }

    public List<Favourite> getProductFavourites(User user){
        user = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("An error has ocurred"));
        return user.getFavourite();
    }

}

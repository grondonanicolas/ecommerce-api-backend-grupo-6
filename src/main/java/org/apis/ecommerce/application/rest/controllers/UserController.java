package org.apis.ecommerce.application.rest.controllers;


import lombok.AllArgsConstructor;
import org.apis.ecommerce.application.rest.dtos.UserDTO;
import org.apis.ecommerce.domain.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws Exception {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}

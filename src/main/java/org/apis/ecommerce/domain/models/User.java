package org.apis.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = false, name = "image_url")
    private String imageURL;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Historic> historic;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Favourite> favourite;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getUsername() {
        return this.email;
    }

    public void addProductToHistoric(Historic h){
        if(this.historic == null ){
            this.historic =  new ArrayList<>();
        }
        if (hasHistoric(h) ){
            Historic existingHistoric = this.historic.stream()
            .filter(his -> his.getProduct().getId().equals(h.getProduct().getId()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("El producto en el historial no se ha encontrado")); 
            existingHistoric.setUpdatedAt(LocalDateTime.now());
            return;
        }
        this.historic.add(h);
    }

    private boolean hasHistoric(Historic h){
       return this.historic.stream().anyMatch(hist -> hist.getProduct().getId().equals(h.getProduct().getId()));
    }

    public void addProductToFavourite(Favourite f){
        if(this.favourite == null ){
            this.favourite =  new ArrayList<>();
        } 
        if (hasFavourite(f)){
            // throw new IllegalStateException("El producto ya se encuentra como favorito");
            return;
        }
        this.favourite.add(f);
    }

    private boolean hasFavourite(Favourite f){
       return this.favourite.stream().anyMatch(fav -> fav.getProduct().getId().equals(f.getProduct().getId()));
    }

    public void removeProductFromFavourite(Favourite f) {
        if (this.favourite != null) {
                Favourite favouriteToRemove = this.favourite.stream()
                    .filter(fav -> fav.getProduct().getId().equals(f.getProduct().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("El producto favorito no se encuentra"));
                this.favourite.remove(favouriteToRemove);
        } else {
            throw new IllegalArgumentException("No hay productos en favoritos para eliminar");
        }
    }
}

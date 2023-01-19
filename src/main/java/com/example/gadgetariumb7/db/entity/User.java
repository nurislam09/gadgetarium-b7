package com.example.gadgetariumb7.db.entity;

import javax.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Builder
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 3)
    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    @Column(length = 10000)
    private String image;
    private String address;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH})
    private List<Product> basketList;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH})
    private List<Product> favoritesList;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE}, mappedBy = "user")
    private List<Review> userReviews;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH})
    private List<Product> compareProductsList;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH})
    private List<Product> orderHistoryList;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH})
    private List<Product> viewedProductsList;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH}, mappedBy = "user")
    private List<Order> orders;

    @ManyToOne(cascade = {PERSIST, REFRESH, MERGE, DETACH})
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        return grantedAuthorities;  }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

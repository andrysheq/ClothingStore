package com.web.auction.models;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@RequiredArgsConstructor
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    //@NotBlank(message="Поле 'Логин' не может быть пустым")
    private final String username;
    //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Некорректный формат пароля")
    private final String password;
    //@NotBlank(message="Поле 'Имя' не может быть пустым")
    private final String fullName;
    //@NotBlank(message="Поле 'Улица' не может быть пустым")
    private final String street;
    //@NotBlank(message="Поле 'Город' не может быть пустым")
    private final String city;
    //@NotBlank(message="Поле 'Область' не может быть пустым")
    private final String state;
    //@NotBlank(message="Поле 'Индекс' не может быть пустым")
    private final String zip;
    //@Pattern(regexp = "\\+\\d{11}", message = "Некорректный формат номера телефона")
    private final String phoneNumber;
    private byte[] photo;
    private boolean accountNonLocked;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    // Добавьте методы для управления ролями, например, добавление/удаление
    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }
    public boolean hasRole(String id){
        for(Role role : roles){
            if(role.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPhoto(byte[] photo){
        this.photo = photo;
    }
}

package com.web.auction.models;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
public class Role implements GrantedAuthority {
    @Id
    private final String id;

    private String authority; // Роль, например, "ROLE_USER", "ROLE_ADMIN"

    public Role(String id, String authority){
        this.id = id;
        this.authority = authority;
    }
}

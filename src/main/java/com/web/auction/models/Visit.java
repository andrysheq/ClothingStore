package com.web.auction.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Visit{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Date visitedAt;

    @PrePersist
    void createdAt() {
        this.visitedAt = new Date();
    }
}

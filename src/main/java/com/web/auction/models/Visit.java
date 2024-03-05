package com.web.auction.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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

package com.ycyw.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer", nullable = false)
    private AppUser customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "support", nullable = false)
    private AppUser support;
}

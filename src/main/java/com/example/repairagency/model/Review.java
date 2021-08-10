package com.example.repairagency.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    @Column(name="review_description")
    @Lob
    private String reviewDescription;
    @ManyToOne
    @JoinColumn (name="master_id")
    private AppUser master;
}

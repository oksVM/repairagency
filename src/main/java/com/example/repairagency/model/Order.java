package com.example.repairagency.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "area")
    private Area area;
    @Column(name="order_description")
    @Lob
    private String orderDescription;
    @Column(name = "price")
    private Integer price;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private AppUser customer;
    @ManyToOne
    @JoinColumn (name="master_id")
    private AppUser master;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "offset_data_time")
    private OffsetDateTime offsetDateTime;

}


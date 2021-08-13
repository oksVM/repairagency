package com.example.repairagency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Column(name="order_name")
    private String orderName;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "area")
    private Area area;
    @Column(name="order_description")
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
    //@Temporal(TemporalType.TIMESTAMP)
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Paris")
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "offset_data_time")
    private OffsetDateTime offsetDateTime;


    public Order(UserDetails loadUserByUsername) {
    }

    public Order(UserDetails loadUserByUsername, String orderDescription) {
    }

    public Order(UserDetails loadUserByUsername, String orderDescription, OrderStatus waitForAdminConfirmation) {
    }

    public Order(String orderDescription, OrderStatus waitForAdminConfirmation) {
    }

    public Order(OrderStatus waitForAdminConfirmation) {
    }
}


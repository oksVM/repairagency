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
    @Column(name = "service_item")
   private ServiceItem serviceItem;
   @Column(name = "amount")
   private Integer amount;
     @Enumerated(value = EnumType.STRING)
     @Column(name = "order_status")
     private OrderStatus orderStatus;
    @ManyToOne
    @JoinColumn (name="app_user_id")
    private AppUser customer;
   // @ManyToOne
    //@JoinColumn (name="app_user_id")
   // private AppUser master;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="offset_data_time")
    private OffsetDateTime offsetDateTime;
    //private Review review;


    public Order(Integer amount) {
        //this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}


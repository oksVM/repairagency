package com.example.repairagency.controller;

import com.example.repairagency.entity.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private static final List<Customer> CUSTOMERS = Stream.of(
            new Customer(1L, "Oksana", "Moisiuk"),
             new Customer(2L, "Kate", "Alieva"),
            new Customer(3L, "AMria", "Jones")
    ).collect(Collectors.toList());

    @GetMapping
    public List<Customer> getAll(){
        return CUSTOMERS;
    }

    @GetMapping(path ="{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Long customerId){
//return userId;
      return CUSTOMERS.stream()
                .filter(customer -> customerId.equals(customer.getCustomerId()))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Student doesn`t exist"));
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer){
        this.CUSTOMERS.add(customer);
        return customer;
    }

    @DeleteMapping("{customerId}")
    public void deleteById(@PathVariable Long customerId){
        this.CUSTOMERS.removeIf(customer -> customerId.equals(customer.getCustomerId()));
    }

}

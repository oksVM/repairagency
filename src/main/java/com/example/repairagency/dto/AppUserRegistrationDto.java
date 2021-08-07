package com.example.repairagency.dto;


import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Size;

@Data
public class AppUserRegistrationDto {

    //@Size(min = 5, max = 50)
    private String firstName;
    //@Size(min = 5, max = 50)
    private String lastName;
    //@ValidEmail
    //@Size(min = 5, max = 50)
    private String email;
    //@Size(min = 5, max = 50)
    private String password;


}

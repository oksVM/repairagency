package com.example.repairagency.dto;


import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Size;

@Data
public class AppUserRegistrationDto {

    @Size(min = 5, max = 50, message = "FirstName should contain 5-50 symbols")
    private String firstName;
    @Size(min = 5, max = 50, message = "LastName should contain 5-50 symbols")
    private String lastName;
    @ValidEmail
    private String email;
    @Size(min = 5, max = 50, message = "Password should contain 5-50 symbols")
    private String password;


}

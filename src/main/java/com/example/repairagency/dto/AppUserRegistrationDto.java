package com.example.repairagency.dto;


import com.example.repairagency.annotation.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class AppUserRegistrationDto {

    @Size(min = 3, max = 50, message = "first.name.should.be")
    private String firstName;
    @Size(min = 5, max = 50, message = "last.name.should.be")
    private String lastName;
    @ValidEmail
    private String email;
    @Size(min = 5, max = 50, message = "password.should.be")
    private String password;


}

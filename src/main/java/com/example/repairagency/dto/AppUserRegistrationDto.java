package com.example.repairagency.dto;


import com.example.repairagency.annotation.ValidEmail;
import com.example.repairagency.annotation.ValidName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserRegistrationDto {
@ValidName
    //@Size(min = 3, max = 50, message = "first.name.should.be")
    private String firstName;
    //@Size(min = 5, max = 50, message = "last.name.should.be")
    @ValidName
    private String lastName;
    @ValidEmail
    private String email;
    @Size(min = 5, max = 50, message = "password.should.be")
    private String password;


}

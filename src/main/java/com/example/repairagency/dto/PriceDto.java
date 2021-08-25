package com.example.repairagency.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Data
public class PriceDto {
    @NotNull(message = "cannot be null.")
    @Min(value = 1)
    private Integer amountOfMoney;

}


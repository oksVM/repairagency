package com.example.repairagency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositDTO {
        @NotNull(message = "cannot be null.")
        @Min(value = 1)
        private Integer amountOfMoney;

}

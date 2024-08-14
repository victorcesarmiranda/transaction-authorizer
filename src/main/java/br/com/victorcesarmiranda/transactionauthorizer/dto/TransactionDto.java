package br.com.victorcesarmiranda.transactionauthorizer.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TransactionDto(@NotBlank String account,
                             @NotNull BigDecimal totalAmount,
                             @Pattern(regexp = "\\d{4}") String mcc,
                             @NotBlank @Size(max = 40) String merchant) {
}
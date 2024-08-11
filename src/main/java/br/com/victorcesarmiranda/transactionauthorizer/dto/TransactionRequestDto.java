package br.com.victorcesarmiranda.transactionauthorizer.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransactionRequestDto(@NotBlank String account,
                                    @NotNull BigDecimal totalAmount,
                                    @NotBlank String mcc,
                                    @NotBlank String merchant) {
}
package br.com.victorcesarmiranda.transactionauthorizer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionResult {

    APPROVED("00"),
    REJECTED("51"),
    ERROR("07");

    final String code;
}

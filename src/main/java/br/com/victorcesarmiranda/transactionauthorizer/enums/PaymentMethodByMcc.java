package br.com.victorcesarmiranda.transactionauthorizer.enums;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentMethodByMcc {

    FOOD(List.of("5411", "5412")),
    MEAL(List.of("5811", "5812")),
    CASH(List.of());

    final List<String> mcc;

    public static PaymentMethodByMcc fromMcc(String mcc) {
        for (PaymentMethodByMcc paymentMethodByMcc : PaymentMethodByMcc.values()) {
            if (paymentMethodByMcc.mcc.contains(mcc)) {
                return paymentMethodByMcc;
            }
        }
        return CASH;
    }
}

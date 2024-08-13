package br.com.victorcesarmiranda.transactionauthorizer.service.payment.strategy;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.victorcesarmiranda.transactionauthorizer.enums.PaymentMethodByMcc;
import br.com.victorcesarmiranda.transactionauthorizer.model.Account;
import br.com.victorcesarmiranda.transactionauthorizer.service.payment.PaymentExecutor;

public interface PaymentStrategy {

    void pay(Account account, BigDecimal amount);

    PaymentMethodByMcc getPaymentMcc();

    @Autowired
    default void registerStrategy(PaymentExecutor paymentExecutor) {
        paymentExecutor.registerPaymentStrategy(getPaymentMcc(), this);
    }


}

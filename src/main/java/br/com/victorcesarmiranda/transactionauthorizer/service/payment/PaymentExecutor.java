package br.com.victorcesarmiranda.transactionauthorizer.service.payment;

import java.math.BigDecimal;

import br.com.victorcesarmiranda.transactionauthorizer.enums.PaymentMethodByMcc;
import br.com.victorcesarmiranda.transactionauthorizer.model.Account;
import br.com.victorcesarmiranda.transactionauthorizer.service.payment.strategy.PaymentStrategy;

public interface PaymentExecutor {

    void processPayment(PaymentMethodByMcc mcc, Account account, BigDecimal amount);

    void registerPaymentStrategy(PaymentMethodByMcc paymentMethodByMcc, PaymentStrategy paymentStrategy);
}

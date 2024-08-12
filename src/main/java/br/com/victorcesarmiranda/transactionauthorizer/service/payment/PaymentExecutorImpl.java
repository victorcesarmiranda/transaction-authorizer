package br.com.victorcesarmiranda.transactionauthorizer.service.payment;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import br.com.victorcesarmiranda.transactionauthorizer.enums.PaymentMethodByMcc;
import br.com.victorcesarmiranda.transactionauthorizer.exception.InsufficientBalanceException;
import br.com.victorcesarmiranda.transactionauthorizer.model.Account;
import br.com.victorcesarmiranda.transactionauthorizer.service.payment.strategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentExecutorImpl implements PaymentExecutor {
    private final Map<PaymentMethodByMcc, PaymentStrategy> strategies = new EnumMap<>(PaymentMethodByMcc.class);

    @Override
    @Retryable(retryFor = InsufficientBalanceException.class, maxAttempts = 1)
    public void processPayment(PaymentMethodByMcc mcc, Account account, BigDecimal amount) {
        strategies.get(mcc).pay(account, amount);
    }


    @Recover
    public void recoverPayment(InsufficientBalanceException e, PaymentMethodByMcc mcc, Account account, BigDecimal amount) {
        strategies.get(PaymentMethodByMcc.CASH).pay(account, amount);
    }


    @Override
    public void registerPaymentStrategy(PaymentMethodByMcc mcc, PaymentStrategy strategy) {
        strategies.put(mcc, strategy);
    }
}

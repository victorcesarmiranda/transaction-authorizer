package br.com.victorcesarmiranda.transactionauthorizer.service.payment;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.victorcesarmiranda.transactionauthorizer.enums.PaymentMethodByMcc;
import br.com.victorcesarmiranda.transactionauthorizer.exception.InsufficientBalanceException;
import br.com.victorcesarmiranda.transactionauthorizer.model.Account;
import br.com.victorcesarmiranda.transactionauthorizer.service.payment.strategy.PaymentStrategy;

@ExtendWith(MockitoExtension.class)
class PaymentExecutorImplTest {

    @Mock
    private PaymentStrategy paymentStrategy;

    @InjectMocks
    private PaymentExecutorImpl paymentExecutor;

    @Test
    void processPayment_ValidPayment_ProcessesPayment() {
        Account account = new Account();
        BigDecimal amount = BigDecimal.valueOf(100);
        paymentExecutor.registerPaymentStrategy(PaymentMethodByMcc.MEAL, paymentStrategy);

        paymentExecutor.processPayment(PaymentMethodByMcc.MEAL, account, amount);

        then(paymentStrategy).should().pay(account, amount);
    }

    @Test
    void recoverPayment_InsufficientBalance_ProcessesPaymentWithCash() {
        Account account = new Account();
        BigDecimal amount = BigDecimal.valueOf(100);
        PaymentStrategy cashStrategy = mock(PaymentStrategy.class);
        paymentExecutor.registerPaymentStrategy(PaymentMethodByMcc.CASH, cashStrategy);

        paymentExecutor.recoverPayment(new InsufficientBalanceException(), PaymentMethodByMcc.MEAL, account, amount);

        then(cashStrategy).should().pay(account, amount);
    }

    @Test
    void registerPaymentStrategy_ValidPaymentStrategy_RegistersPaymentStrategy() {
        assertDoesNotThrow(() -> paymentExecutor.registerPaymentStrategy(PaymentMethodByMcc.MEAL, paymentStrategy));
    }
}
package br.com.victorcesarmiranda.transactionauthorizer.service.payment.strategy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.victorcesarmiranda.transactionauthorizer.enums.PaymentMethodByMcc;
import br.com.victorcesarmiranda.transactionauthorizer.model.Account;
import br.com.victorcesarmiranda.transactionauthorizer.service.account.AccountSevice;
import br.com.victorcesarmiranda.transactionauthorizer.service.payment.PaymentExecutor;

@ExtendWith(MockitoExtension.class)
class CashPaymentTest {

    @Mock
    private PaymentExecutor paymentExecutor;

    @Mock
    private AccountSevice accountService;

    @InjectMocks
    private CashPayment cashPayment;

    @Test
    void pay_ValidAccountAndAmount_CallsDebitFromCash() {
        Account account = new Account();
        BigDecimal amount = BigDecimal.valueOf(100);

        cashPayment.pay(account, amount);

        then(accountService).should().debitFromCash(account, amount);
    }

    @Test
    void getPaymentMcc_Always_ReturnsCash() {
        assertEquals(PaymentMethodByMcc.CASH, cashPayment.getPaymentMcc());
    }

    @Test
    void registerStrategy_ValidPaymentExecutor_RegistersPaymentStrategy() {
        cashPayment.registerStrategy(paymentExecutor);

        then(paymentExecutor).should().registerPaymentStrategy(cashPayment.getPaymentMcc(), cashPayment);
    }

}
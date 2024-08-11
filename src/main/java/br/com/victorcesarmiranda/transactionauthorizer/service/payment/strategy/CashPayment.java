package br.com.victorcesarmiranda.transactionauthorizer.service.payment.strategy;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.victorcesarmiranda.transactionauthorizer.enums.PaymentMethodByMcc;
import br.com.victorcesarmiranda.transactionauthorizer.model.Account;
import br.com.victorcesarmiranda.transactionauthorizer.service.account.AccountSevice;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CashPayment implements PaymentStrategy {

    private final AccountSevice accountSevice;

    @Override
    public void pay(Account account, BigDecimal amount) {
        accountSevice.debitFromCash(account, amount);
    }

    @Override
    public PaymentMethodByMcc getPaymentMcc() {
        return PaymentMethodByMcc.CASH;
    }
}

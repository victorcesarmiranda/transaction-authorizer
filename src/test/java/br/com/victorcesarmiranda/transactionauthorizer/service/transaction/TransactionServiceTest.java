package br.com.victorcesarmiranda.transactionauthorizer.service.transaction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.victorcesarmiranda.transactionauthorizer.dto.TransactionDto;
import br.com.victorcesarmiranda.transactionauthorizer.enums.PaymentMethodByMcc;
import br.com.victorcesarmiranda.transactionauthorizer.model.Account;
import br.com.victorcesarmiranda.transactionauthorizer.model.Transaction;
import br.com.victorcesarmiranda.transactionauthorizer.repository.TransactionRepository;
import br.com.victorcesarmiranda.transactionauthorizer.service.account.AccountSevice;
import br.com.victorcesarmiranda.transactionauthorizer.service.payment.PaymentExecutor;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountSevice accountService;

    @Mock
    private PaymentExecutor paymentExecutor;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void processTransaction_ValidMealTransaction_ProcessesPaymentAsMealAndSavesTransaction() {
        var transactionDto = new TransactionDto("12345", BigDecimal.valueOf(100), "5812", "Merchant");
        Account account = new Account();
        account.setNumber("12345");
        BDDMockito.given(accountService.findByNumber(transactionDto.account())).willReturn(account);

        transactionService.processTransaction(transactionDto);

        then(paymentExecutor).should().processPayment(PaymentMethodByMcc.MEAL, account, transactionDto.totalAmount());
        then(transactionRepository).should().save(any(Transaction.class));
    }

    @Test
    void processTransaction_InvalidMcc_ProcessesPaymentAsCachAndSavesTransaction() {
        var transactionDto = new TransactionDto("12345", BigDecimal.valueOf(100), "8125", "Merchant");
        Account account = new Account();
        account.setNumber("12345");
        BDDMockito.given(accountService.findByNumber(transactionDto.account())).willReturn(account);

        transactionService.processTransaction(transactionDto);

        then(paymentExecutor).should().processPayment(PaymentMethodByMcc.CASH, account, transactionDto.totalAmount());
        then(transactionRepository).should().save(any(Transaction.class));
    }
}
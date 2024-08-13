package br.com.victorcesarmiranda.transactionauthorizer.service.transaction;

import org.springframework.stereotype.Service;

import br.com.victorcesarmiranda.transactionauthorizer.dto.TransactionDto;
import br.com.victorcesarmiranda.transactionauthorizer.enums.PaymentMethodByMcc;
import br.com.victorcesarmiranda.transactionauthorizer.model.Transaction;
import br.com.victorcesarmiranda.transactionauthorizer.repository.TransactionRepository;
import br.com.victorcesarmiranda.transactionauthorizer.service.account.AccountSevice;
import br.com.victorcesarmiranda.transactionauthorizer.service.payment.PaymentExecutor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountSevice accountSevice;
    private final PaymentExecutor paymentExecutor;

    public void processTransaction(TransactionDto transactionDto) {
        var account = accountSevice.findByNumber(transactionDto.account());
        var paymentMethodByMcc = PaymentMethodByMcc.fromMcc(transactionDto.mcc());
        paymentExecutor.processPayment(paymentMethodByMcc, account, transactionDto.totalAmount());
        transactionRepository.save(new Transaction(account, transactionDto));
    }

}

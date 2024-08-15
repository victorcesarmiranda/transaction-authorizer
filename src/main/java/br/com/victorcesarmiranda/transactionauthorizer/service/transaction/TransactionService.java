package br.com.victorcesarmiranda.transactionauthorizer.service.transaction;

import java.util.Arrays;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.victorcesarmiranda.transactionauthorizer.dto.TransactionDto;
import br.com.victorcesarmiranda.transactionauthorizer.enums.PaymentMethodByMcc;
import br.com.victorcesarmiranda.transactionauthorizer.model.Transaction;
import br.com.victorcesarmiranda.transactionauthorizer.repository.TransactionRepository;
import br.com.victorcesarmiranda.transactionauthorizer.service.account.AccountSevice;
import br.com.victorcesarmiranda.transactionauthorizer.service.merchant.MerchantService;
import br.com.victorcesarmiranda.transactionauthorizer.service.payment.PaymentExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountSevice accountSevice;
    private final PaymentExecutor paymentExecutor;

    private final MerchantService merchantService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processTransaction(TransactionDto transactionDto) {
        try {
            var account = accountSevice.findByNumber(transactionDto.account());
            PaymentMethodByMcc paymentMethodByMcc = getPaymentMethodByMcc(transactionDto);
            paymentExecutor.processPayment(paymentMethodByMcc, account, transactionDto.totalAmount());
            transactionRepository.save(new Transaction(account, transactionDto));
        } catch (ObjectOptimisticLockingFailureException e) {
            log.warn("Error processing concurrent transaction in account: {}", transactionDto.account());
            throw e;
        }
    }

    private PaymentMethodByMcc getPaymentMethodByMcc(TransactionDto transactionDto) {
        String paymentMethod = merchantService.getPaymentMethodByMerchant(transactionDto.merchant());
        return Arrays.stream(PaymentMethodByMcc.values())
                .filter(method -> method.name().equals(paymentMethod))
                .findAny()
                .orElse(PaymentMethodByMcc.fromMcc(transactionDto.mcc()));
    }

}

package br.com.victorcesarmiranda.transactionauthorizer.service.account;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.com.victorcesarmiranda.transactionauthorizer.exception.InsufficientBalanceException;
import br.com.victorcesarmiranda.transactionauthorizer.model.Account;
import br.com.victorcesarmiranda.transactionauthorizer.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountSevice {

    private final AccountRepository accountRepository;

    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number)
                .orElseThrow(() -> new NoSuchElementException("Account not found"));
    }

    public void debitFromFood(Account account, BigDecimal amount) {
        if (!account.canDebitFromFood(amount)) {
            throw new InsufficientBalanceException();
        }
        account.debitFromFood(amount);
        accountRepository.save(account);
    }

    public void debitFromMeal(Account account, BigDecimal amount) {
        if (!account.canDebitFromMeal(amount)) {
            throw new InsufficientBalanceException();
        }
        account.debitFromMeal(amount);
        accountRepository.save(account);
    }

    public void debitFromCash(Account account, BigDecimal amount) {
        if (!account.canDebitFromCash(amount)) {
            throw new InsufficientBalanceException();
        }
        account.debitFromCash(amount);
        accountRepository.save(account);
    }
}

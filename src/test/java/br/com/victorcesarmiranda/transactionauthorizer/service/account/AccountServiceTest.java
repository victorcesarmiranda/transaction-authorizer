package br.com.victorcesarmiranda.transactionauthorizer.service.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.victorcesarmiranda.transactionauthorizer.exception.InsufficientBalanceException;
import br.com.victorcesarmiranda.transactionauthorizer.model.Account;
import br.com.victorcesarmiranda.transactionauthorizer.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountSevice accountService;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    @Test
    void findByNumber_AccountExists_ReturnsAccount() {
        String accountNumber = "12345";
        Account account = new Account();
        BDDMockito.given(accountRepository.findByNumber(accountNumber)).willReturn(Optional.of(account));

        Account result = accountService.findByNumber(accountNumber);

        BDDMockito.then(accountRepository).should().findByNumber(accountNumber);
        assert result.equals(account);
    }

    @Test
    void debitFromFood_SufficientBalance_DeductsAmount() {
        Account account = new Account();
        account.setFoodBalance(BigDecimal.valueOf(100));
        BigDecimal amount = BigDecimal.valueOf(100);

        accountService.debitFromFood(account, amount);

        BDDMockito.then(accountRepository).should().save(accountCaptor.capture());
        assertEquals(BigDecimal.valueOf(0), accountCaptor.getValue().getFoodBalance());
    }

    @Test
    void debitFromFood_InsufficientBalance_ThrowsException() {
        Account account = new Account();
        account.setFoodBalance(BigDecimal.valueOf(50));
        BigDecimal amount = BigDecimal.valueOf(100);

        assertThrows(InsufficientBalanceException.class, () -> accountService.debitFromFood(account, amount));
    }

    @Test
    void debitFromMeal_SufficientBalance_DeductsAmount() {
        Account account = new Account();
        account.setMealBalance(BigDecimal.valueOf(100));
        BigDecimal amount = BigDecimal.valueOf(100);

        accountService.debitFromMeal(account, amount);

        BDDMockito.then(accountRepository).should().save(accountCaptor.capture());
        assertEquals(BigDecimal.valueOf(0), accountCaptor.getValue().getMealBalance());
    }

    @Test
    void debitFromMeal_InsufficientBalance_ThrowsException() {
        Account account = new Account();
        account.setMealBalance(BigDecimal.valueOf(50));
        BigDecimal amount = BigDecimal.valueOf(100);

        assertThrows(InsufficientBalanceException.class, () -> accountService.debitFromMeal(account, amount));
    }

    @Test
    void debitFromCash_SufficientBalance_DeductsAmount() {
        Account account = new Account();
        account.setCashBalance(BigDecimal.valueOf(100));
        BigDecimal amount = BigDecimal.valueOf(100);

        accountService.debitFromCash(account, amount);

        BDDMockito.then(accountRepository).should().save(accountCaptor.capture());
        assertEquals(BigDecimal.valueOf(0), accountCaptor.getValue().getCashBalance());
    }

    @Test
    void debitFromCash_InsufficientBalance_ThrowsException() {
        Account account = new Account();
        account.setCashBalance(BigDecimal.valueOf(50));
        BigDecimal amount = BigDecimal.valueOf(100);

        assertThrows(InsufficientBalanceException.class, () -> accountService.debitFromCash(account, amount));
    }

}
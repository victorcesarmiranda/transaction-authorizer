package br.com.victorcesarmiranda.transactionauthorizer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.victorcesarmiranda.transactionauthorizer.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String number);
}

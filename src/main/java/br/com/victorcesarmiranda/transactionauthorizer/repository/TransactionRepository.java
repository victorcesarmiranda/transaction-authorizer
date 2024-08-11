package br.com.victorcesarmiranda.transactionauthorizer.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.victorcesarmiranda.transactionauthorizer.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}

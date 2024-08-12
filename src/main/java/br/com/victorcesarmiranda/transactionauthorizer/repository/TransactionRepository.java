package br.com.victorcesarmiranda.transactionauthorizer.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.victorcesarmiranda.transactionauthorizer.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}

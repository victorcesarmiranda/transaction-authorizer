package br.com.victorcesarmiranda.transactionauthorizer.model;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.victorcesarmiranda.transactionauthorizer.dto.TransactionRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Account account;
    private BigDecimal amount;
    private String merchant;
    private String mcc;

    public Transaction(Account account, TransactionRequestDto dto) {
        this.account = account;
        this.amount = dto.totalAmount();
        this.merchant = dto.merchant();
        this.mcc = dto.mcc();
    }
}
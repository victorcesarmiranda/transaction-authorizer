package br.com.victorcesarmiranda.transactionauthorizer.model;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.victorcesarmiranda.transactionauthorizer.dto.TransactionDto;
import jakarta.persistence.Column;
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

    @Column(length = 40)
    private String merchant;

    @Column(length = 4)
    private String mcc;

    public Transaction(Account account, TransactionDto dto) {
        this.account = account;
        this.amount = dto.totalAmount();
        this.merchant = dto.merchant();
        this.mcc = dto.mcc();
    }
}
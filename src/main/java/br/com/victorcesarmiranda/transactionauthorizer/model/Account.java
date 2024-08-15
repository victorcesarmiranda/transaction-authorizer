package br.com.victorcesarmiranda.transactionauthorizer.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private BigDecimal foodBalance;
    private BigDecimal mealBalance;
    private BigDecimal cashBalance;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    @Version
    private Long version;

    public boolean canDebitFromFood(BigDecimal amount) {
        return this.foodBalance.compareTo(amount) >= 0;
    }

    public boolean canDebitFromMeal(BigDecimal amount) {
        return this.mealBalance.compareTo(amount) >= 0;
    }

    public boolean canDebitFromCash(BigDecimal amount) {
        return this.cashBalance.compareTo(amount) >= 0;
    }

    public void debitFromFood(BigDecimal amount) {
        this.foodBalance = this.foodBalance.subtract(amount);
    }

    public void debitFromMeal(BigDecimal amount) {
        this.mealBalance = this.mealBalance.subtract(amount);
    }

    public void debitFromCash(BigDecimal amount) {
        this.cashBalance = this.cashBalance.subtract(amount);
    }
}

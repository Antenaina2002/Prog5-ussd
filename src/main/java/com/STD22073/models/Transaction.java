package com.STD22073.models;
import com.STD22073.enums.TransactionType;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private TransactionType type;
    private double amount;
    private LocalDateTime date;
    private String description;

    public Transaction(TransactionType type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %.2f MGA - %s",
                date, type, amount, description);
    }
}

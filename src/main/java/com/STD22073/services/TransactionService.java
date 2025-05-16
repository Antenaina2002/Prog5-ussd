package com.STD22073.services;

import com.STD22073.enums.TransactionType;
import com.STD22073.models.Transaction;
import com.STD22073.models.User;

public class TransactionService {
    public void addTransaction(User user, TransactionType type, double amount, String description) {
        user.getTransactions().add(new Transaction(type, amount, description));
        if (type == TransactionType.DEPOSIT) {
            user.setBalance(user.getBalance() + amount);
        } else {
            user.setBalance(user.getBalance() - amount);
        }
    }

    public boolean transfer(User sender, User receiver, double amount) {
        if (sender.getBalance() >= amount) {
            addTransaction(sender, TransactionType.TRANSFER, amount, "Transfer to " + receiver.getPhoneNumber());
            addTransaction(receiver, TransactionType.DEPOSIT, amount, "Transfer from " + sender.getPhoneNumber());
            return true;
        }
        return false;
    }
}

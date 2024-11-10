package com.fastandreckless.bankapplication.service;

import com.fastandreckless.bankapplication.model.Account;
import com.fastandreckless.bankapplication.model.Transfer;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BankService {

    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    public Account createAccount(String accountId) {
        Account account = new Account(accountId);
        accounts.put(accountId, account);
        return account;
    }

    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public void deposit(String accountId, long amount) {
        Account account = getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }
        account.deposit(amount);
    }

    public void withdraw(String accountId, long amount) {
        Account account = getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }
        account.withdraw(amount);
    }

    public void transfer(String fromAccountId, String toAccountId, long amount) {
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }
        Account fromAccount = getAccount(fromAccountId);
        Account toAccount = getAccount(toAccountId);
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Account not found.");
        }

        synchronized (fromAccountId.compareTo(toAccountId) < 0 ? fromAccount : toAccount) {
            synchronized (fromAccountId.compareTo(toAccountId) < 0 ? toAccount : fromAccount) {
                if (fromAccount.getBalance() < amount) {
                    throw new IllegalArgumentException("Insufficient funds.");
                }
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
                Transfer transfer = new Transfer(fromAccountId, toAccountId, amount);
                fromAccount.addTransfer(transfer);
            }
        }
    }

    public Set<Account> getAllAccounts() {
        return new HashSet<>(accounts.values());
    }

}

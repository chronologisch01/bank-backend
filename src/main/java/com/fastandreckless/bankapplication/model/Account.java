package com.fastandreckless.bankapplication.model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

public class Account {
    private final String accountId;
    //https://docs.oracle.com/javase/8/docs/api/?java/util/concurrent/atomic/AtomicLong.html
    private final AtomicLong balance;
    private final Deque<Transfer> lastTransfers;

    public Account(String accountId) {
        this.accountId = accountId;
        this.balance = new AtomicLong(0);
        this.lastTransfers = new LinkedList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public long getBalance() {
        return balance.get();
    }

    public void deposit(long amount) {
        balance.addAndGet(amount);
    }

    public void withdraw(long amount) {
        balance.addAndGet(-amount);
    }

    public void addTransfer(Transfer transfer) {
        if (lastTransfers.size() == 50) {
            lastTransfers.removeFirst();
        }
        lastTransfers.addLast(transfer);

    }

    public Deque<Transfer> getLastTransfers() {
        return lastTransfers;
    }
}
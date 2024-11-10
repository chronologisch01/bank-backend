package com.fastandreckless.bankapplication.model;

public class Transfer {
    private final String fromAccountId;
    private final String toAccountId;
    private final long amount;

    public Transfer(String fromAccountId, String toAccountId, long amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public long getAmount() {
        return amount;
    }
}
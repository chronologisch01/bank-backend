package com.fastandreckless.bankapplication.controller;

import com.fastandreckless.bankapplication.model.Account;
import com.fastandreckless.bankapplication.model.Transfer;
import com.fastandreckless.bankapplication.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:3000")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/create/{accountId}")
    public ResponseEntity<String> createAccount(@PathVariable String accountId) {
        bankService.createAccount(accountId);
        return ResponseEntity.ok("Account created.");
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String accountId, @RequestParam long amount) {
        bankService.deposit(accountId, amount);
        return ResponseEntity.ok("Deposit successful.");
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String accountId, @RequestParam long amount) {
        bankService.withdraw(accountId, amount);
        return ResponseEntity.ok("Withdrawal successful.");
    }

    @PostMapping("/{accountId}/transfer")
    public ResponseEntity<String> transfer(
            @PathVariable String accountId,
            @RequestParam String toAccountId,
            @RequestParam long amount) {
        bankService.transfer(accountId, toAccountId, amount);
        return ResponseEntity.ok("Transfer successful.");
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountId) {
        Account account = bankService.getAccount(accountId);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @GetMapping
    public Set<Account> getAllAccounts() {
        return bankService.getAllAccounts();
    }

    @GetMapping("/{accountId}/transfers")
    public ResponseEntity<List<Transfer>> getLastTransfers(@PathVariable String accountId) {
        Account account = bankService.getAccount(accountId);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        List<Transfer> transfers = account.getLastTransfers().stream().toList();
        return ResponseEntity.ok(transfers);
    }
}
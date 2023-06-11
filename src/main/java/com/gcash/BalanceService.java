package com.gcash;

public class BalanceService {
    private final AccountRepository accountRepository;

    // Constructs a BalanceService with the specified AccountRepository.
    public BalanceService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Retrieves the balance of the account with the specified ID.
    // Returns the balance if the account exists, otherwise returns null.
    public Double getBalance(String id) {
        Account account = accountRepository.getAccount(id);
        if (account != null) {
            return account.getBalance();
        }
        return null;
    }

    // Debits the specified amount from the account with the specified ID.
    // Throws an IllegalArgumentException if the account does not exist
    // or if the account has insufficient balance for the debit operation.
    public void debit(String id, Double amount) {
        Account account = accountRepository.getAccount(id);
        if (account != null) {
            Double currentBalance = account.getBalance();
            if (currentBalance >= amount) {
                account.setBalance(currentBalance - amount);
            } else {
                throw new IllegalArgumentException("Insufficient balance for debit operation.");
            }
        }
    }

    // Credits the specified amount to the account with the specified ID.
    // If the account exists, the balance is updated by adding the amount.
    public void credit(String id, Double amount) {
        Account account = accountRepository.getAccount(id);
        if (account != null) {
            Double currentBalance = account.getBalance();
            account.setBalance(currentBalance + amount);
        }
    }

    // Transfers the specified amount from one account to another.
    // Throws an IllegalArgumentException if either account does not exist
    // or if the source account has insufficient balance for the transfer operation.
    public void transfer(String from, String to, Double amount) {
        Account fromAccount = accountRepository.getAccount(from);
        Account toAccount = accountRepository.getAccount(to);
        if (fromAccount != null && toAccount != null) {
            Double fromBalance = fromAccount.getBalance();
            if (fromBalance >= amount) {
                fromAccount.setBalance(fromBalance - amount);
                toAccount.setBalance(toAccount.getBalance() + amount);
            } else {
                throw new IllegalArgumentException("Insufficient balance for transfer operation.");
            }
        }
    }
}

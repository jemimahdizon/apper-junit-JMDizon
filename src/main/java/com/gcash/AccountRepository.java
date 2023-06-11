package com.gcash;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountRepository {
    // List to store the accounts
    private final List<Account> accounts = new ArrayList<>();

    // Creates a new account with the specified name and initial balance.
    // Generates a unique ID for the account and adds it to the list of accounts.
    public String createAccount(String name, Double initialBalance) {
        String id = UUID.randomUUID().toString();
        Account account = new Account(id, name, initialBalance);
        accounts.add(account);
        return id;
    }

    // Retrieves the account with the specified ID.
    public Account getAccount(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }

    // Deletes the account with the specified ID from the list of accounts.
    public void deleteAccount(String id) {
        Account accountToRemove = null;
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                accountToRemove = account;
                break;
            }
        }
        if (accountToRemove != null) {
            accounts.remove(accountToRemove);
        }
    }

    // Retrieves the number of registered accounts.
    public Integer getNumberOfAccounts() {
        return accounts.size();
    }

    // Checks if there are no registered accounts.
    public boolean noRegisteredAccount() {
        return accounts.isEmpty();
    }
}

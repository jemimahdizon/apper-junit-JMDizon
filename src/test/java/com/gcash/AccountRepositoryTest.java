package com.gcash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountRepositoryTest {

    @Test
    void successfulAccountCreation() {
        AccountRepository repository = new AccountRepository();

        // Create an account with name "Mai" and initial balance of 89.9
        String accountId = repository.createAccount("Mai", 89.9);

        // Verify that the account was created successfully
        Assertions.assertEquals(1, repository.getNumberOfAccounts());
        Assertions.assertEquals("Mai", repository.getAccount(accountId).getName());
        Assertions.assertNotNull(accountId);
    }

    @Test
    void successfulGetAccount() {
        AccountRepository repository = new AccountRepository();

        // Create an account with name "Mai" and initial balance of 89.9
        String accountId = repository.createAccount("Mai", 89.9);

        // Verify that the account can be retrieved successfully
        Assertions.assertEquals("Mai", repository.getAccount(accountId).getName());
        Assertions.assertEquals(89.9, repository.getAccount(accountId).getBalance());

        // Verify that a non-existent account returns null
        Assertions.assertNull(repository.getAccount("randomid"));
    }

    @Test
    void successfulDeleteAccount() {
        AccountRepository repository = new AccountRepository();

        // Create an account with name "Mai" and initial balance of 89.9
        String accountId = repository.createAccount("Mai", 89.9);

        // Delete the account
        repository.deleteAccount(accountId);

        // Verify that the account was deleted successfully
        Assertions.assertEquals(0, repository.getNumberOfAccounts());
        Assertions.assertNull(repository.getAccount(accountId));
    }

    @Test
    void successfulGetNumberOfAccounts() {
        AccountRepository repository = new AccountRepository();

        // Create three accounts with different names and balances
        String id0 = repository.createAccount("Mai", 89.9);
        String id1 = repository.createAccount("John", 50.0);
        String id2 = repository.createAccount("Jane", 100.0);

        // Verify that the number of accounts is correct
        Assertions.assertEquals(3, repository.getNumberOfAccounts());

        // Delete one of the accounts
        repository.deleteAccount(id1);

        // Verify that the number of accounts is updated after deletion
        Assertions.assertEquals(2, repository.getNumberOfAccounts());
    }

    @Test
    void successfulNoRegisteredAccount() {
        AccountRepository repository = new AccountRepository();

        // Verify that there are no registered accounts initially
        Assertions.assertTrue(repository.noRegisteredAccount());

        // Create an account
        repository.createAccount("Mai", 89.9);

        // Verify that there is at least one registered account
        Assertions.assertFalse(repository.noRegisteredAccount());
    }
}

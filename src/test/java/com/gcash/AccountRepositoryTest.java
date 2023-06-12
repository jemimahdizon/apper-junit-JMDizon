package com.gcash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AccountRepositoryTest {
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        accountRepository = new AccountRepository();
    }

    @Test
    public void testAccountConstructor_InvalidInitialBalance() {
        // Try to create an account with negative initial balance, it should throw an exception
        assertThrows(IllegalArgumentException.class,
                () -> new Account("1", "Mai", -100.0));
    }

    @Test
    public void testSetBalance_InvalidNewBalance() {
        Account account = new Account("1", "Mai", 100.0);

        // Try to set a negative balance, it should throw an exception
        assertThrows(IllegalArgumentException.class,
                () -> account.setBalance(-50.0));
    }

    @Test
    public void testCreateAccount() {
        // Test creating an account with valid initial balance
        String accountId = accountRepository.createAccount("Mai", 100.0);
        assertNotNull(accountId);
        assertTrue(accountId.length() > 0);
        assertEquals(1, accountRepository.getNumberOfAccounts());
    }

    @Test
    public void testGetAccount_existingAccount() {
        // Create an account and test retrieving it
        String accountId = accountRepository.createAccount("Mai", 100.0);
        Account account = accountRepository.getAccount(accountId);
        assertNotNull(account);
        assertEquals(accountId, account.getId());
        assertEquals("Mai", account.getName());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    public void testGetAccount_nonExistingAccount() {
        // Test retrieving a non-existing account, it should return null
        Account account = accountRepository.getAccount("non_existing_id");
        assertNull(account);
    }

    @Test
    public void testDeleteAccount_existingAccount() {
        // Create an account and delete it, then verify the account is removed from the repository
        String accountId = accountRepository.createAccount("Mai", 100.0);
        accountRepository.deleteAccount(accountId);
        assertEquals(0, accountRepository.getNumberOfAccounts());
        assertNull(accountRepository.getAccount(accountId));
    }

    @Test
    public void testDeleteAccount_nonExistingAccount() {
        // Try to delete a non-existing account, it should not throw an exception
        assertDoesNotThrow(() -> accountRepository.deleteAccount("non_existing_id"));
    }

    @Test
    public void testGetNumberOfAccounts_emptyRepository() {
        // Test the number of accounts in an empty repository
        assertEquals(0, accountRepository.getNumberOfAccounts());
        assertTrue(accountRepository.noRegisteredAccount());
    }

    @Test
    public void testGetNumberOfAccounts_nonEmptyRepository() {
        // Create multiple accounts and test the number of accounts and the repository not being empty
        accountRepository.createAccount("Mai", 100.0);
        accountRepository.createAccount("Dizon", 200.0);
        assertEquals(2, accountRepository.getNumberOfAccounts());
        assertFalse(accountRepository.noRegisteredAccount());
    }

    @Test
    public void testGetNumberOfAccounts_afterAccountDeletion() {
        // Create an account, delete it, and then test the number of accounts in the repository
        String accountId = accountRepository.createAccount("Mai", 100.0);
        accountRepository.deleteAccount(accountId);
        assertEquals(0, accountRepository.getNumberOfAccounts());
    }

    @Test
    public void testGetAccount_afterAccountDeletion() {
        // Create an account, delete it, and then try to retrieve the deleted account, it should return null
        String accountId = accountRepository.createAccount("Mai", 100.0);
        accountRepository.deleteAccount(accountId);
        Account account = accountRepository.getAccount(accountId);
        assertNull(account);
    }
}

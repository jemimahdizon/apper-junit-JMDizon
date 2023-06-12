package com.gcash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceServiceTest {

    private BalanceService balanceService;
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
        balanceService = new BalanceService(accountRepository);
    }

    @Test
    void testGetBalance() {
        // Verify that the balance of an account is returned correctly.
        String accountId = accountRepository.createAccount("Mai", 100.0);
        Double result = balanceService.getBalance(accountId);
        Assertions.assertEquals(100.0, result);
    }

    @Test
    void testDebitSufficientBalance() {
        // Verify that the balance of an account is correctly updated when debiting a sufficient amount.
        String accountId = accountRepository.createAccount("Mai", 100.0);
        balanceService.debit(accountId, 50.0);
        Account account = accountRepository.getAccount(accountId);
        Assertions.assertEquals(50.0, account.getBalance());
    }

    @Test
    void testDebitInsufficientBalance() {
        // Verify that attempting to debit an amount larger than the account balance throws an IllegalArgumentException.
        String accountId = accountRepository.createAccount("Mai", 100.0);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> balanceService.debit(accountId, 150.0));
    }

    @Test
    void testCredit() {
        // Verify that the balance of an account is correctly updated when crediting an amount.
        String accountId = accountRepository.createAccount("Mai", 100.0);
        balanceService.credit(accountId, 50.0);
        Account account = accountRepository.getAccount(accountId);
        Assertions.assertEquals(150.0, account.getBalance());
    }

    @Test
    void testTransferSufficientBalance() {
        // Verify that a transfer between two accounts with sufficient balances is performed correctly.
        String fromAccountId = accountRepository.createAccount("Mai", 100.0);
        String toAccountId = accountRepository.createAccount("Mai", 50.0);
        balanceService.transfer(fromAccountId, toAccountId, 75.0);
        Account fromAccount = accountRepository.getAccount(fromAccountId);
        Account toAccount = accountRepository.getAccount(toAccountId);
        Assertions.assertEquals(25.0, fromAccount.getBalance());
        Assertions.assertEquals(125.0, toAccount.getBalance());
    }

    @Test
    void testTransferInsufficientBalance() {
        // Verify that attempting to transfer an amount larger than the source account balance throws an IllegalArgumentException.
        String fromAccountId = accountRepository.createAccount("Mai", 100.0);
        String toAccountId = accountRepository.createAccount("Mai", 50.0);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> balanceService.transfer(fromAccountId, toAccountId, 150.0));
    }

    @Test
    public void testGetBalance_existingAccount() {
        // Verify that the balance of an existing account is returned correctly.
        String accountId = accountRepository.createAccount("Mai", 100.0);
        Double balance = balanceService.getBalance(accountId);
        Assertions.assertEquals(100.0, balance);
    }

    @Test
    public void testGetBalance_nonExistingAccount() {
        // Verify that attempting to get the balance of a non-existing account returns null.
        Double balance = balanceService.getBalance("non_existing_id");
        Assertions.assertNull(balance);
    }

    @Test
    public void testGetBalance_nonExistingAccountReturnsNull() {
        // Verify that attempting to get the balance of a non-existing account returns null.
        Double balance = balanceService.getBalance("non_existing_id");
        Assertions.assertNull(balance);
    }
}

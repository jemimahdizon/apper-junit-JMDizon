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
        // Setup: Create an account with name "Mai" and initial balance of 100.0
        String accountId = accountRepository.createAccount("Mai", 100.0);

        // Verify: Get the balance of the account and assert it is 100.0
        Double result = balanceService.getBalance(accountId);
        Assertions.assertEquals(100.0, result);
    }

    @Test
    void testDebitSufficientBalance() {
        // Setup: Create an account with name "Mai" and initial balance of 100.0
        String accountId = accountRepository.createAccount("Mai", 100.0);

        // Kick: Debit 50.0 from the account
        balanceService.debit(accountId, 50.0);

        // Verify: Get the updated balance of the account and assert it is 50.0
        Account account = accountRepository.getAccount(accountId);
        Assertions.assertEquals(50.0, account.getBalance());
    }

    @Test
    void testDebitInsufficientBalance() {
        // Setup: Create an account with name "Mai" and initial balance of 100.0
        String accountId = accountRepository.createAccount("Mai", 100.0);

        // Verify: Attempt to debit 150.0 from the account and expect an IllegalArgumentException
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> balanceService.debit(accountId, 150.0));
    }

    @Test
    void testCredit() {
        // Setup: Create an account with name "Mai" and initial balance of 100.0
        String accountId = accountRepository.createAccount("Mai", 100.0);

        // Kick: Credit 50.0 to the account
        balanceService.credit(accountId, 50.0);

        // Verify: Get the updated balance of the account and assert it is 150.0
        Account account = accountRepository.getAccount(accountId);
        Assertions.assertEquals(150.0, account.getBalance());
    }

    @Test
    void testTransferSufficientBalance() {
        // Setup: Create two accounts with names "Mai" and initial balances of 100.0 and 50.0
        String fromAccountId = accountRepository.createAccount("Mai", 100.0);
        String toAccountId = accountRepository.createAccount("Mai", 50.0);

        // Kick: Transfer 75.0 from one account to the other
        balanceService.transfer(fromAccountId, toAccountId, 75.0);

        // Verify: Get the updated balances of both accounts and assert the expected values
        Account fromAccount = accountRepository.getAccount(fromAccountId);
        Account toAccount = accountRepository.getAccount(toAccountId);
        Assertions.assertEquals(25.0, fromAccount.getBalance());
        Assertions.assertEquals(125.0, toAccount.getBalance());
    }

    @Test
    void testTransferInsufficientBalance() {
        // Setup: Create two accounts with names "Mai" and initial balances of 100.0 and 50.0
        String fromAccountId = accountRepository.createAccount("Mai", 100.0);
        String toAccountId = accountRepository.createAccount("Mai", 50.0);

        // Verify: Attempt to transfer 150.0 from one account to the other and expect an IllegalArgumentException
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> balanceService.transfer(fromAccountId, toAccountId, 150.0));
    }
}

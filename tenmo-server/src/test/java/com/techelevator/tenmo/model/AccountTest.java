package com.techelevator.tenmo.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountTest {

    private Account account;

    @Before
    public void setup() {
        account = new Account(new BigDecimal("100.00"), 1, 1);
    }

    @Test
    public void test_balance_methods(){
        Assert.assertEquals(new BigDecimal("100.00"), account.getBalance());

        account.setBalance(new BigDecimal("200.00"));

        Assert.assertEquals(new BigDecimal("200.00"), account.getBalance());
    }

    @Test
    public void test_accountId_methods(){
        Assert.assertEquals(1, account.getAccountId());

        account.setAccountId(2);

        Assert.assertEquals(2, account.getAccountId());
    }

    @Test
    public void test_userId_methods(){
        Assert.assertEquals(1, account.getUserId());

        account.setUserId(2);

        Assert.assertEquals(2, account.getUserId());
    }

}

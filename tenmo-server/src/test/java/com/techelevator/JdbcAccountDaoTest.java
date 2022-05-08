package com.techelevator;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.List;

public class JdbcAccountDaoTest extends BaseDaoTests{

    private static final Account ACCOUNT_1 = new Account(new BigDecimal("1000.00"), 2001, 1001 );
    private static final Account ACCOUNT_2 = new Account(new BigDecimal("2000.00"), 2002, 1002 );
    private static final Account ACCOUNT_3 = new Account(new BigDecimal("3000.00"), 2003, 1003 );

    private static final String ACCOUNT_1_USERNAME = "User1";
    private static final String ACCOUNT_2_USERNAME = "User2";
    private static final String ACCOUNT_3_USERNAME = "User3";

    private JdbcTemplate jdbcTemplate;
    private JdbcAccountDao sut;

//    private AccountDao sut;

    @Before
    public void setup() {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void check_balance_returns_correct_balance(){
        BigDecimal balance = sut.checkBalance(1001);

        Assert.assertEquals(new BigDecimal("1000.00"),balance);
    }

}

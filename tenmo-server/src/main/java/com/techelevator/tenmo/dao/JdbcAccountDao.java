package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal checkBalance(int user_id) {
        BigDecimal balance = null;

        String sql = "SELECT * FROM account WHERE user_id = ?";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, user_id);

        if (results.next()) {
            balance = results.getBigDecimal("balance");
        }

        return balance;
    }

    @Override
    public List<User> listUsers(int user_id) {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM tenmo_user WHERE user_id != ?;";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, user_id);

        while(results.next()) {
            users.add(userMapper(results));
        }

        return users;
    }

    private Account accountMapper(SqlRowSet results) {
        Account account = new Account();

        account.setBalance(results.getBigDecimal("balance"));
        account.setUserId(results.getLong("user_id"));
        account.setAccountId(results.getLong("account_id"));

        return account;
    }

    private User userMapper(SqlRowSet results) {
        User user = new User();

        user.setId(results.getLong("user_id"));
        user.setUsername(results.getString("username"));

        return user;
    }

}

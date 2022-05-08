package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountDao {

    BigDecimal checkBalance(int user_id);

    List<User> listUsers(int user_id);

}

package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.TransactionDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TEnmoController {

    @Autowired
    UserDao userDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    TransactionDao transactionDao;



    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {
        return accountDao.checkBalance(userDao.findIdByUsername(principal.getName()));
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> listUsers(Principal principal) {
        return accountDao.listUsers(userDao.findIdByUsername(principal.getName()));
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public String transfer(Principal principal,@Valid @RequestBody TransactionDTO transactionDTO) {
        return transactionDao.transfer(transactionDTO.getAmount(), transactionDTO.getAccount_to_id(), userDao.findIdByUsername(principal.getName()), transactionDTO.getTransfer_type_desc());
    }

    @RequestMapping(path = "/transferlog", method = RequestMethod.GET)
    public List<TransactionDTO> transferLog(Principal principal) {
        return transactionDao.viewTransfers(userDao.findIdByUsername(principal.getName()));
    }
}

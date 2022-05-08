package com.techelevator.tenmo.model;

import com.techelevator.tenmo.dao.UserDao;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.security.Principal;

public class TransactionDTO {

    private UserDao userDao;
    private Principal principal;

    private int transfer_id;

    @NotEmpty
    private String transfer_type_desc;

    private String transfer_status_desc;

    private String account_from;

    private String account_to;

    @NotNull
    private int account_to_id;

    @Positive
    private BigDecimal amount;

    public TransactionDTO() {
    }

    public TransactionDTO(int transfer_id, String transfer_type_desc, String transfer_status_desc, String account_from, String account_to, int account_to_id, BigDecimal amount) {
        this.transfer_id = transfer_id;
        this.transfer_type_desc = transfer_type_desc;
        this.transfer_status_desc = transfer_status_desc;
        this.account_from = account_from;
        this.account_to = account_to;
        this.account_to_id = account_to_id;
        this.amount = amount;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public String getTransfer_status_desc() {
        return transfer_status_desc;
    }

    public void setTransfer_status_desc(String transfer_status_desc) {
        this.transfer_status_desc = transfer_status_desc;
    }

    public String getAccount_from() {
        return account_from;
    }

    public void setAccount_from(String account_from) {
        this.account_from = account_from;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getAccount_to_id() {
        return account_to_id;
    }

    public void setAccount_to_id(int account_to_id) {
        this.account_to_id = account_to_id;
    }

    public String getTransfer_type_desc() {
        return transfer_type_desc;
    }

    public void setTransfer_type_desc(String transfer_type_desc) {
        this.transfer_type_desc = transfer_type_desc;
    }

    public String getAccount_to() {
        return account_to;
    }

    public void setAccount_to(String account_to) {
        this.account_to = account_to;
    }
}

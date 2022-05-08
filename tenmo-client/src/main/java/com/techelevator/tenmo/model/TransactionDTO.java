package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Objects;

public class TransactionDTO {

    Principal principal;

    private int transfer_id;
    private String transfer_type_desc;
    private String transfer_status_desc;
    private String account_from;
    private String account_to;
    private BigDecimal amount;
    private int account_to_id;

    public int getAccount_to_id() {
        return account_to_id;
    }

    public void setAccount_to_id(int account_to_id) {
        this.account_to_id = account_to_id;
    }

    public String getTransfer_status_desc() {
        return transfer_status_desc;
    }

    public void setTransfer_status_desc(String transfer_status_desc) {
        this.transfer_status_desc = transfer_status_desc;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
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

    public String getAccount_to() {
        return account_to;
    }

    public void setAccount_to(String account_to) {
        this.account_to = account_to;
    }

    public String getTransfer_type_desc() {
        return transfer_type_desc;
    }

    public void setTransfer_type_desc(String transfer_type_desc) {
        this.transfer_type_desc = transfer_type_desc;
    }

    public String viewTransferLog(String username){
        String fromTo = "";
        if (this.account_from.equals(username)){
            fromTo = "To:   " + account_to;
        }else {
            fromTo = "From: " + account_from;
        }
        return (transfer_id + "        " + fromTo + "             $ " + amount);
    }

    public String viewTransferDetails(){
        String transferDetails = "--------------------------------\n" +
                "Transfer Details\n" + "--------------------------------\n" +
                "Id: " + transfer_id + "\nFrom: " + account_from +
                "\nTo: " + account_to + "\nType: " + transfer_type_desc +
                "\nStatus: " + transfer_status_desc + "\nAmount: $" + amount;
        return transferDetails;
    }
}

package com.techelevator.tenmo.model;

import com.techelevator.tenmo.model.TransactionDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TransactionDTOTests {

    private TransactionDTO tDTO;

    @Before
    public void setup(){
        tDTO = new TransactionDTO(1, "Send", "Approved", "FromUser", "ToUser", 111, new BigDecimal("150.00"));
    }


    @Test
    public void test_transfer_id_methods(){
        Assert.assertEquals(1, tDTO.getTransfer_id());

        tDTO.setTransfer_id(2);

        Assert.assertEquals(2, tDTO.getTransfer_id());
    }

    @Test
    public void test_transfer_type_desc_methods(){
        Assert.assertEquals("Send", tDTO.getTransfer_type_desc());

        tDTO.setTransfer_type_desc("Request");

        Assert.assertEquals("Request", tDTO.getTransfer_type_desc());
    }

    @Test
    public void test_transfer_status_desc_methods(){
        Assert.assertEquals("Approved", tDTO.getTransfer_status_desc());

        tDTO.setTransfer_status_desc("Rejected");

        Assert.assertEquals("Rejected", tDTO.getTransfer_status_desc());
    }

    @Test
    public void test_account_from_methods(){
        Assert.assertEquals("FromUser", tDTO.getAccount_from());

        tDTO.setAccount_from("Nobody");

        Assert.assertEquals("Nobody", tDTO.getAccount_from());
    }

    @Test
    public void test_account_to_methods(){
        Assert.assertEquals("ToUser", tDTO.getAccount_to());

        tDTO.setAccount_to("Nobody");

        Assert.assertEquals("Nobody", tDTO.getAccount_to());
    }

    @Test
    public void test_account_to_id_methods(){
        Assert.assertEquals(111, tDTO.getAccount_to_id());

        tDTO.setAccount_to_id(222);

        Assert.assertEquals(222, tDTO.getAccount_to_id());
    }

    @Test
    public void test_amount_methods(){
        Assert.assertEquals(new BigDecimal("150.00"), tDTO.getAmount());

        tDTO.setAmount(new BigDecimal("250.00"));

        Assert.assertEquals(new BigDecimal("250.00"), tDTO.getAmount());
    }

}

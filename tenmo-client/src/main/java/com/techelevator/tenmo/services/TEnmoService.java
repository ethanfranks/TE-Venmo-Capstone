package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransactionDTO;
import com.techelevator.tenmo.model.User;
import org.apiguardian.api.API;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.temporal.Temporal;
import java.util.*;

public class TEnmoService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private HttpEntity<TransactionDTO> makeTransactionEntity(TransactionDTO transactionDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<TransactionDTO> entity = new HttpEntity<>(transactionDTO, headers);

        return entity;
    }

    private HttpEntity<Void> makeAuthEntity() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return entity;
    }

    public BigDecimal getBalance() {

        BigDecimal balance = new BigDecimal("0.00");

        try {
            balance = restTemplate.exchange(
                    API_BASE_URL + "balance",
                    HttpMethod.GET,
                    makeAuthEntity(),
                    BigDecimal.class
            ).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            e.printStackTrace();
        }

        return balance;
    }

    public User[] listUsers() {

        User[] users = null;

        users = restTemplate.exchange(
                API_BASE_URL + "users",
                HttpMethod.GET,
                makeAuthEntity(),
                User[].class
        ).getBody();

        return users;
    }

    public String transfer(TransactionDTO transactionDTO) {
        String response = "";

        try {
            response = restTemplate.exchange(
                    API_BASE_URL + "transfer",
                    HttpMethod.POST,
                    makeTransactionEntity(transactionDTO),
                    String.class
            ).getBody();
//        }catch(MethodArgumentNotValidException e) {
//
        } catch (RestClientResponseException | ResourceAccessException e) {
//            System.out.println("ERROR!");
        }
        return response;
    }

    public TransactionDTO[] transferLog() {

        TransactionDTO[] transactionDTOS = null;

        transactionDTOS = restTemplate.exchange(
                API_BASE_URL + "transferlog",
                HttpMethod.GET,
                makeAuthEntity(),
                TransactionDTO[].class
        ).getBody();

        return transactionDTOS;
    }
}

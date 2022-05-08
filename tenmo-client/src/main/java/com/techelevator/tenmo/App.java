package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransactionDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TEnmoService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private TEnmoService tEnmoService = new TEnmoService();
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            System.out.println("");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        boolean keepGoing = true;
        do {
            try {
                UserCredentials credentials = consoleService.promptForCredentials();
                currentUser = authenticationService.login(credentials);
                tEnmoService.setAuthToken(currentUser.getToken());
                keepGoing = false;
            } catch (NullPointerException e) {
                System.out.println("\nPlease enter valid credentials\n");
            }
        } while (keepGoing);

        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            System.out.println("");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        // TODO Auto-generated method stub
        System.out.println("Your current account balance is: $" + tEnmoService.getBalance());
    }

    private void viewTransferHistory() {
        TransactionDTO[] log = null;
        log = tEnmoService.transferLog();

        if (log.length == 0) {
            System.out.println("You have no transfer history!");
            return;
        } else {
            boolean keepGoing = true;
            do {
                consoleService.printViewTransfers();
                for (TransactionDTO tDTO : log) {
                    System.out.println(tDTO.viewTransferLog(currentUser.getUser().getUsername()));
                }
                System.out.println("---------");
                Integer choice = 0;

                try {
                    System.out.println("\nPlease enter transfer ID to view details (0 to cancel): ");
                    System.out.print("Transfer ID: ");
                    choice = Integer.parseInt(scanner.nextLine());
                    System.out.println();
                    boolean idFound = false;

                    if (choice == 0) {
                        keepGoing = false;
                    } else {
                        for (TransactionDTO tDTO : log) {
                            if (tDTO.getTransfer_id() == choice) {
                                System.out.println(tDTO.viewTransferDetails());

                                keepGoing = false;
                                idFound = true;
                            }
                        }
                        if (!idFound){
                            System.out.println("Invalid input!\n");
                        }
                    }

                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid input!\n");
                }
            } while (keepGoing);
        }
    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }

    private void sendBucks() {
        User[] displayedUsers = null;
        displayedUsers = tEnmoService.listUsers();

        int account_to_id = 0;
        BigDecimal amount = new BigDecimal("0.00");
        boolean keepRunning = true;
        String response = "";

        System.out.println("");

        do {
            try {
                consoleService.printSendBucks();
                for (User user : displayedUsers) {
                    System.out.println(user);
                }
                System.out.println("---------");
                System.out.println("\nEnter ID of user you are sending to (0 to cancel):");
                System.out.print("Receiver ID: ");
                account_to_id = Integer.parseInt(scanner.nextLine());

                if (account_to_id == 0) {
                    return;
                }
                System.out.println("\nCurrent Balance: $" + tEnmoService.getBalance());
                System.out.print("Enter amount:");
                amount = new BigDecimal(scanner.nextLine());

                        TransactionDTO transactionDTO = new TransactionDTO();
                        transactionDTO.setAmount(amount);
                        transactionDTO.setAccount_to_id(account_to_id);
                        transactionDTO.setTransfer_type_desc("Send");

                        //Set response message to server response
                        response = tEnmoService.transfer(transactionDTO);

                        if (response.contains("Transaction complete.")) {
                            //Stop the loop
                            keepRunning = false;
                        }

                if (response.equals("")) {
                    System.out.println("\nInvalid input!\n");
                } else {
                    System.out.println("\n" + response);
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input!\n");
            }
        } while (keepRunning);
    }

    private void requestBucks() {
        // TODO Auto-generated method stub
    }
}

package services;

import dao.*;
import daoservices.*;
import model.card.*;
import model.account.*;
import model.user.*;
import utils.*;

import java.sql.SQLException;
import java.util.*;
import java.text.ParseException;

public class ApplicationService {
    private AccountRepositoryService accountDatabaseService;
    private TransactionRepositoryService transactionDatabaseService;
    private UserRepositoryService userDatabaseService;
    private CardRepositoryService cardDatabaseService;

    private final AccountSeparation accountSeparation = new AccountSeparation();
    private final UserSeparation userSeparation = new UserSeparation();
    private final CardSeparation cardSeparation = new CardSeparation();
    private final Map<String, Account> accountsMap = new HashMap<>();

    public ApplicationService() throws SQLException {
        this.accountDatabaseService = new AccountRepositoryService();
        this.transactionDatabaseService = new TransactionRepositoryService();
        this.userDatabaseService = new UserRepositoryService();
        this.cardDatabaseService = new CardRepositoryService();
    }

    public void mapAccounts() throws SQLException{
        for (var account : accountDatabaseService.getAccounts()) {
            accountsMap.put(account.getIBAN(), account);
        }
    }

    private User getUserFromInput(Scanner scanner) throws Exception{
        if (userDatabaseService.getNumberOfUsers() == 0) {
            throw new Exception("No users have beed added!");
        } else if (userDatabaseService.getNumberOfUsers() == 1) {
            return userDatabaseService.getUserByID(0);
        } else {
            System.out.println("User id: [0-" + userDatabaseService.getNumberOfUsers() + "]:");
            int id = Integer.parseInt(scanner.nextLine());
            return userDatabaseService.getUserByID(id);
        }
    }

    public void printUser(Scanner scanner) throws Exception{
        var user = getUserFromInput(scanner);
        System.out.println(user);
    }
    
    public void createUser(Scanner scanner) throws SQLException, ParseException {
        User newUser = userSeparation.createUser(scanner);
        userDatabaseService.addUser(newUser);
        Account newAccount = accountSeparation.createAccount(newUser.getFirstName() + " " + newUser.getLastName(), newUser.getUserID());
        accountDatabaseService.addAccount(newAccount);
        System.out.println("User created");
    }


    private Account getAccountFromInput(Scanner scanner, User user) throws Exception {
        List<Account> usersAccounts = user.filterAccounts(accountDatabaseService.getAllAccounts());
        System.out.println("User accounts: " + usersAccounts);
        System.out.println("Select IBAN: ");
        var IBAN = scanner.nextLine();
        if (!accountsMap.containsKey(IBAN)) {
            throw new Exception("Invalid IBAN!");
        }
        var account = accountsMap.get(IBAN);
        if (account.getUserID() != user.getUserID()) {
            throw new Exception("IBAN associated with a different account!");
        }
        return account;
    }

    //DONE
    public void printUserBalance(Scanner scanner) throws Exception {
        var user = getUserFromInput(scanner);
        var userAccounts = user.filterAccounts(accountDatabaseService.getAllAccounts());
        double totalBalance = 0;
        for (var acc : userAccounts) {
            totalBalance += acc.getBalance();
        }
        System.out.println("The total balance of " + user.getFirstName() + " " + user.getLastName() + " is " + totalBalance);
    }

    //DONE
    public void printUserAccounts(Scanner scanner) throws Exception {
        var user = getUserFromInput(scanner);
        List<Account> userAccounts = user.filterAccounts(accountDatabaseService.getAllAccounts());
        System.out.println(userAccounts.toString());
    }

    public void createUserAccount(Scanner scanner) throws Exception {
        var user = getUserFromInput(scanner);
        System.out.println("Name: ");
        String name = scanner.nextLine();
        Account newAccount = accountSeparation.createAccount(name, user.getUserID());
        accountDatabaseService.addAccount(newAccount);
        accountsMap.put(newAccount.getIBAN(), newAccount);
        System.out.println("Account created");
    }

    public void createUserSavingsAccount(Scanner scanner) throws Exception {
        var user = getUserFromInput(scanner);
        System.out.println("Name: ");
        String name = scanner.nextLine();
        SavingsAccount newSavingsAccount = accountSeparation.createSavingsAccount(name, user.getUserID());
        accountDatabaseService.addAccount(newSavingsAccount);
        System.out.println("Savings account created");
    }

    public void createUserCard(Scanner scanner) throws Exception {
        var user = getUserFromInput(scanner);
        var account = getAccountFromInput(scanner, user);
        System.out.println("Name: ");
        String name = scanner.nextLine();
        account.addCard(name);
    }

    public void depositIntoAccount(Scanner scanner) throws Exception {
        var user = getUserFromInput(scanner);
        System.out.println("How much do you want to deposit?");
        double balance = Double.parseDouble(scanner.nextLine());
        var userAccounts = user.filterAccounts(accountDatabaseService.getAllAccounts());
        userAccounts.get(0).setBalance(balance);
        System.out.println("Deposit made successfully!");
    }

    public void makeTransaction(Scanner scanner) throws Exception {
        System.out.println("From IBAN: ");
        String fromIBAN = scanner.nextLine();
        System.out.println("To IBAN: ");
        String toIBAN = scanner.nextLine();
        System.out.println("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.println("Description: ");
        String description = scanner.nextLine();

        Account fromAccount = null;
        Account toAccount = null;

        if (accountsMap.containsKey(fromIBAN))
            fromAccount = accountsMap.get(fromIBAN);
        if (accountsMap.containsKey(toIBAN))
            toAccount = accountsMap.get(toIBAN);

        if (fromAccount == null || toAccount == null)
            throw new Exception("No account with the given IBAN. Please try again!");
        if (fromAccount.getBalance() < amount)
            throw new Exception("Not enough balance to transfer the given amount.");
        if (fromIBAN.equals(toIBAN))
            throw new Exception("Cannot make deposits to the same account.");

        Transaction newTransaction = new Transaction(fromIBAN, toIBAN, description, amount);
        transactionDatabaseService.addTransaction(newTransaction);

        System.out.println("Transaction finished!");
    }

    public void closeAccount(Scanner scanner) throws Exception {
        var user = getUserFromInput(scanner);
        var account = getAccountFromInput(scanner, user);

        if (user.filterAccounts(accountDatabaseService.getAllAccounts()).isEmpty())
            throw new Exception("No account associated with the user.");
        accountsMap.remove(account.getIBAN());
        System.out.println("Enter the type of account you want to close(account/savingsaccount): ");
        String typeOfAccount = scanner.nextLine();
        accountDatabaseService.removeAccount(typeOfAccount, account.getIBAN());
        System.out.println("Account closed successfully!");
    }

    public void getUserAccount(Scanner scanner) throws Exception {
        User user = getUserFromInput(scanner);
        Account account = getAccountFromInput(scanner, user);

        System.out.println(account);
    }

    public void getUserTransactions(Scanner scanner) throws Exception {
        var user = getUserFromInput(scanner);
        System.out.println("Show all transactions? (yes/no)");
        String answer = scanner.nextLine();
        switch (answer) {
            case "yes":
                System.out.println(user.filterTransactions(accountDatabaseService.getAllAccounts(), transactionDatabaseService.getAllTransactions()));
                break;
            case "no":
                System.out.println("Year of transactions: ");
                int year = scanner.nextInt();
                System.out.println(user.filterTransactionsByYear(accountDatabaseService.getAllAccounts(), transactionDatabaseService.getAllTransactions(), year));
                break;
            default:
                throw new Exception("Incorrect command!");
        }
    }

}

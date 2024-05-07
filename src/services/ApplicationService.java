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

        this.mapAccounts();
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
            return userDatabaseService.getUserByID(1);
        } else {
            int noUsers = userDatabaseService.getNumberOfUsers();
            System.out.println("User id: [1-" + noUsers + "]:");
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
        List<Account> usersAccounts = filterAccounts(user, accountDatabaseService.getAccounts());
        System.out.println("User accounts: " + usersAccounts);
        System.out.println("Select IBAN: ");
        String IBAN = scanner.nextLine();
        if (!accountsMap.containsKey(IBAN)) {
            throw new Exception("Invalid IBAN!");
        }
        var account = accountsMap.get(IBAN);
        if (account.getUserID() != user.getUserID()) {
            throw new Exception("IBAN associated with a different account!");
        }
        return account;
    }

    public void printUserBalance(Scanner scanner) throws Exception {
        var userAccounts = filterAccounts(scanner, accountDatabaseService.getAccounts());
        double totalBalance = 0;
        for (var acc : userAccounts) {
            totalBalance += acc.getBalance();
        }
        System.out.println("The total balance of the given user is " + totalBalance);
    }

    //DONE
    public void printUserAccounts(Scanner scanner) throws Exception {
        List<Account> userAccounts = filterAccounts(scanner, accountDatabaseService.getAccounts());
        System.out.println("Accounts: ");
        System.out.println(userAccounts.toString());
        List<SavingsAccount> savings = filterSavingsAccounts(scanner, accountDatabaseService.getSavingsAccounts());
        System.out.println("Saving accounts: ");
        System.out.println(savings.toString());
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
        User user = getUserFromInput(scanner);
        Account account = getAccountFromInput(scanner, user);
        addCard(scanner, account);
    }

    public void addCard(Scanner scanner, Account account) throws SQLException{
        System.out.println("Select the type of card: MasterCard/Visa");
        String selection = scanner.nextLine();
        if (selection.toLowerCase().equals("mastercard")) {
            Card card = cardSeparation.createMasterCard(account.getIBAN(), account.getName());
            cardDatabaseService.addCard(card);
            System.out.println("Card created");
        } else if (selection.toLowerCase().equals("visa")) {
            Card card = cardSeparation.createVisaCard(account.getIBAN(), account.getName());
            cardDatabaseService.addCard(card);
            System.out.println("Card created");
        } else {
            System.out.println("Invalid card type!");
        }
    }

    public void depositIntoAccount(Scanner scanner) throws Exception {
        List<Account> userAccounts = filterAccounts(scanner, accountDatabaseService.getAccounts());
        System.out.println("How much do you want to deposit?");
        double balance = Double.parseDouble(scanner.nextLine());
        userAccounts.get(0).setBalance(userAccounts.get(0).getBalance() + balance);
        accountDatabaseService.updateAccount(userAccounts.get(0));
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

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        Transaction newTransaction = new Transaction(fromIBAN, toIBAN, description, amount);
        transactionDatabaseService.addTransaction(newTransaction);

        if(accountDatabaseService != null) {
            accountDatabaseService.updateAccount(fromAccount);
            accountDatabaseService.updateAccount(toAccount);
        }

        System.out.println("Transaction finished!");
    }

    public void closeAccount(Scanner scanner) throws Exception {
        var user = getUserFromInput(scanner);
        var account = getAccountFromInput(scanner, user);

        if (filterAccounts(user, accountDatabaseService.getAccounts()).isEmpty())
            throw new Exception("No account associated with the user.");
        accountsMap.remove(account.getIBAN());
        System.out.println("Enter the type of account you want to close(account/savingsaccount): ");
        String typeOfAccount = scanner.nextLine();
        accountDatabaseService.removeAccount(typeOfAccount, account.getIBAN());
        System.out.println("Account closed successfully!");
    }


    public void getUserTransactions(Scanner scanner) throws Exception {
        var user = getUserFromInput(scanner);
        System.out.println(filterTransactions(user, accountDatabaseService.getAccounts(), transactionDatabaseService.getTransactions()));
    }

    public List<Transaction> filterTransactions(User user, List<Account> allAccounts, List<Transaction> allTransactions) throws Exception {
        var transactions = new ArrayList<Transaction>();
        var accounts = filterAccounts(user, allAccounts);
        for(var account: accounts)
            transactions.addAll(getTransactionsPerAccount(account, allTransactions));
        return transactions;
    }

    public List<Transaction> getTransactionsPerAccount(Account account, List<Transaction> allTransactions) {
        List<Transaction> transactions = new ArrayList<>();

        for (Transaction transaction : allTransactions) {
            if (transaction.getFromIBAN().equals(account.getIBAN())) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public List<Account> filterAccounts(User user, List<Account> allAccounts) throws Exception {
        List<Account> filteredAccounts = new ArrayList<>();
        for (Account acc : allAccounts)
            if (acc.getUserID() == user.getUserID())
                filteredAccounts.add(acc);
        return filteredAccounts;
    }

    public List<Account> filterAccounts(Scanner scanner, List<Account> allAccounts) throws Exception {
        User user = getUserFromInput(scanner);
        List<Account> filteredAccounts = new ArrayList<>();
        for (Account acc : allAccounts)
            if (acc.getUserID() == user.getUserID())
                filteredAccounts.add(acc);
        return filteredAccounts;
    }

    public List<SavingsAccount> filterSavingsAccounts(Scanner scanner, List<SavingsAccount> allAccounts) throws Exception {
        User user = getUserFromInput(scanner);
        List<SavingsAccount> filteredAccounts = new ArrayList<>();
        for (SavingsAccount acc : allAccounts)
            if (acc.getUserID() == user.getUserID())
                filteredAccounts.add(acc);
        return filteredAccounts;
    }

}

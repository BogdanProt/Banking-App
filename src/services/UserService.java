package services;

import java.text.ParseException;
import java.util.*;

import daoservices.AccountRepositoryService;
import model.account.Account;
import model.user.*;
import utils.*;
import daoservices.UserRepositoryService;
import java.sql.*;

public class UserService {
    private UserRepositoryService userDatabaseService;
    private AccountRepositoryService accountDatabaseService;
    private final UserSeparation userFactory = new UserSeparation();
    private final AccountSeparation accountFactory = new AccountSeparation();

    public UserService() throws SQLException {
        this.userDatabaseService = new UserRepositoryService();
        this.accountDatabaseService = new AccountRepositoryService();
    }

    public void createUser(Scanner scanner) throws SQLException, ParseException {
        User newUser = userFactory.createUser(scanner);
        userDatabaseService.addUser(newUser);
        Account newAccount = accountFactory.createAccount(newUser.getFirstName() + " " + newUser.getLastName(), newUser.getUserID());
        accountDatabaseService.addAccount(newAccount);
        System.out.println("User created");
    }

    public User getUserFromInput(Scanner scanner) throws SQLException {
        if (userDatabaseService.getNumberOfUsers() == 0) {
            System.out.println("No users have been added!");
        } else if (userDatabaseService.getNumberOfUsers() == 1) {
            return userDatabaseService.getUserByID(0);
        } else {
            System.out.println("User id: [0 - " + userDatabaseService.getNumberOfUsers() + "]");
            int id = Integer.parseInt(scanner.nextLine());
            return userDatabaseService.getUserByID(id);
        }
        return null;
    }

    public void printUser(Scanner scanner) throws SQLException{
        User user = getUserFromInput(scanner);
        System.out.println(user);
    }

    public List<Account> filterAccountsPerUser(Scanner scanner) throws SQLException {
        User user = getUserFromInput(scanner);
        List<Account> allAccounts = accountDatabaseService.getAccounts();
        List<Account> filteredAccounts = new ArrayList<>();
        for (Account acc : allAccounts)
            if (acc.getUserID() == user.getUserID())
                filteredAccounts.add(acc);
        return filteredAccounts;
    }

    public void printUserBalance(Scanner scanner) throws SQLException {
        List<Account> userAccounts = filterAccountsPerUser(scanner);
        double totalBalance = 0;
        for (Account acc : userAccounts)
            totalBalance += acc.getBalance();
        System.out.println("The total balance of the given user is: " + totalBalance);
    }

    public void printUserAccounts(Scanner scanner) throws Exception {
        List<Account> userAccounts = filterAccountsPerUser(scanner);
        System.out.println(userAccounts.toString());
    }
}

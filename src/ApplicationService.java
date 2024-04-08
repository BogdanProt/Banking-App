
import dao.*;
import daoservices.*;
import model.card.*;
import model.account.*;
import model.user.*;
import utils.*;
import java.util.*;
import java.text.ParseException;

public class ApplicationService {
    private AccountRepositoryService accountDatabaseService;
    private CardRepositoryService cardDatabaseService;
    private TransactionRepositoryService transactionDatabaseService;
    private UserRepositoryService userDatabaseService;

    private final AccountSeparation accountSeparation = new AccountSeparation();
    private final CardSeparation cardSeparation = new CardSeparation();
    private final UserSeparation userSeparation = new UserSeparation();
    private final Map<String, Account> accountsMap = new HashMap<>();

    public ApplicationService() {
        this.accountDatabaseService = new AccountRepositoryService();
        this.cardDatabaseService = new CardRepositoryService();
        this.transactionDatabaseService = new TransactionRepositoryService();
        this.userDatabaseService = new UserRepositoryService();
    }

    public void mapAccounts() {
        for (var account : accountDatabaseService.getAllAccounts()) {
            accountsMap.put(account.getIBAN(), account);
        }
    }

    private User getUserFromInput(Scanner scanner) throws Exception{
        if (userDatabaseService.getNumberOfUsers() == 0) {
            throw new Exception("No users have beed added!");
        } else if (userDatabaseService.getNumberOfUsers() == 1) {
            return userDatabaseService.getUserByID(0);
        } else {
            System.out.println("User id: [0-\"+(userDatabaseService.getNumberOfUsers - 1)+\"]:");
            int id = Integer.parseInt(scanner.nextLine());
            return userDatabaseService.getUserByID(id);
        }
    }

    public void printUser(Scanner scanner) throws Exception{
        var user = getUserFromInput(scanner);
        System.out.println(user);
    }

    public void createUser(Scanner scanner) throws ParseException {
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

    public void printUserBalance(Scanner scanner) throws Exception {
        var user = getUserFromInput(scanner);
        var userAccounts = user.filterAccounts(accountDatabaseService.getAllAccounts());
        double totalBalance = 0;
        for (var acc : userAccounts) {
            totalBalance += acc.getBalance();
        }
        System.out.println("The total balance of " + user.getFirstName() + " " + user.getLastName() + " is " + totalBalance);
    }

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

}


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

}

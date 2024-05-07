
import services.ApplicationService;

import java.sql.SQLException;
import java.util.*;

public class Application {

    static List<String> commands = Arrays.asList("create_user", "create_user_account", "create_user_savingsaccount", "create_user_card", "print_user", "print_user_balance", "print_user_accounts", "deposit_into_account", "print_user_transactions", "make_transaction", "close_account", "help", "quit");
    static List<String> commandsDescription = Arrays.asList("Create new user", "Create user account", "Create user savings account", "Create user card", "Print user details", "Print user balance", "Print user accounts", "Deposit into account", "Print user transactions", "Make transaction", "Close account", "Print commands", "Quit application");

    private static void printCommands() {
        for (int i=0; i<commands.size(); i++)
            System.out.println("-> " + commandsDescription.get(i) + "(" + commands.get(i) + ")");
        System.out.println();
    }

    public static void main(String[] args) throws SQLException, Exception{

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        ApplicationService applicationService = new ApplicationService();
        System.out.println("Insert command: (help - to see available commands)");
        while (!quit) {
            String command = scanner.nextLine().toLowerCase();

            try {


                switch (command) {
                    case "create_user":
                        applicationService.createUser(scanner);
                        scanner.nextLine();
                        break;
                    case "create_user_account":
                        applicationService.createUserAccount(scanner);
                        break;
                    case "create_user_savingsaccount":
                        applicationService.createUserSavingsAccount(scanner);
                        break;
                    case "create_user_card":
                        applicationService.createUserCard(scanner);
                        break;
                    case "print_user":
                        applicationService.printUser(scanner);
                        break;
                    case "print_user_balance":
                        applicationService.printUserBalance(scanner);
                        break;
                    case "print_user_accounts":
                        applicationService.printUserAccounts(scanner);
                        break;
                    case "deposit_into_account":
                        applicationService.depositIntoAccount(scanner);
                        break;
                    case "print_user_transactions":
                        applicationService.getUserTransactions(scanner);
                        break;
                    case "make_transaction":
                        applicationService.makeTransaction(scanner);
                        break;
                    case "close_account":
                        applicationService.closeAccount(scanner);
                        break;
                    case "help":
                        printCommands();
                        break;
                    case "quit":
                        quit = true;
                        break;
                    default:
                        System.out.println("Wrong command");
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

    }
}
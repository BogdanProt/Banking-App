package daoservices;

import dao.*;
import model.card.*;
import model.account.*;
import model.user.*;
import utils.*;

public class AccountRepositoryService {

    private AccountDAO accountDAO;
    private SavingsAccountDAO savingsAccountDAO;

    public AccountRepositoryService() {
        this.accountDAO = new AccountDAO();
        this.savingsAccountDAO = new SavingsAccountDAO();
    }

    public Account getAccountByID(int userID) {
        Account account = accountDAO.read(userID);
        if (account != null) {
            System.out.println(account);
        } else {
            System.out.println("No account having this ID!");
        }

        return account;
    }

    public SavingsAccount getSavingsAccountByID(int userID) {
        SavingsAccount savingsAccount = savingsAccountDAO.read(userID);
        if (savingsAccount != null) {
            System.out.println(savingsAccount);
        } else {
            System.out.println("No savings account having this ID!");
        }

        return savingsAccount;
    }

    public void removeAccount(String typeOfAccount, int userID) {
        Account account = getAccountByType(typeOfAccount, userID);
        if (account == null) return;

        switch (account.getClass().getSimpleName()) {
            case "Account":
                accountDAO.delete((Account) account);
                break;
            case "SavingsAccount":
                savingsAccountDAO.delete((SavingsAccount) account);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + account);
        }

        System.out.println("Removed: " + account);
    }

    public void addAccount(Account account) {
        if (account != null) {
            switch (account.getClass().getSimpleName()) {
                case "Account":
                    accountDAO.create((Account) account);
                    break;
                case "SavingsAccount":
                    savingsAccountDAO.create((SavingsAccount) account);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + account);
            }
        }

        System.out.println("Added: " + account);
    }

    public Account getAccountByType(String typeOfAccount, int userID) {
        Account account;
        if (typeOfAccount.toLowerCase().equals("account")) {
            account = getAccountByID(userID);
        } else if (typeOfAccount.toLowerCase().equals("savingsaccount")) {
            account = getSavingsAccountByID(userID);
        }
        if (account == null) {
            System.out.println("No account having id: " + userID);
            return null;
        }
        return account;
    }

}

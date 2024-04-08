package daoservices;

import dao.*;
import model.card.*;
import model.account.*;
import model.user.*;
import utils.*;

import java.util.List;

public class AccountRepositoryService {

    private AccountDAO accountDAO;
    private SavingsAccountDAO savingsAccountDAO;

    public AccountRepositoryService() {
        this.accountDAO = new AccountDAO();
        this.savingsAccountDAO = new SavingsAccountDAO();
    }

    public Account getAccountByIBAN(String IBAN) {
        Account account = accountDAO.read(IBAN);
        if (account != null) {
            System.out.println(account);
        } else {
            System.out.println("No account having this ID!");
        }

        return account;
    }

    public SavingsAccount getSavingsAccountByIBAN(String IBAN) {
        SavingsAccount savingsAccount = savingsAccountDAO.read(IBAN);
        if (savingsAccount != null) {
            System.out.println(savingsAccount);
        } else {
            System.out.println("No savings account having this ID!");
        }

        return savingsAccount;
    }

    public void removeAccount(String typeOfAccount, String IBAN) {
        Account account = getAccountByType(typeOfAccount, IBAN);
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

    public Account getAccountByType(String typeOfAccount, String IBAN) {
        Account account;
        if (typeOfAccount.toLowerCase().equals("account")) {
            account = getAccountByIBAN(IBAN);
        } else if (typeOfAccount.toLowerCase().equals("savingsaccount")) {
            account = getAccountByIBAN(IBAN);
        }
        if (account == null) {
            System.out.println("No account having IBAN: " + IBAN);
            return null;
        }
        return account;
    }

    public List<Account> getAllAccounts() {
        if (!accountDAO.getAccounts().isEmpty()) {
            return accountDAO.getAccounts();
        } else {
            System.out.println("No accounts registered!");
        }
        return null;
    }

    public List<SavingsAccount> getAllSavingsAccounts() {
        if (!savingsAccountDAO.getSavingsAccounts().isEmpty()) {
            return savingsAccountDAO.getSavingsAccounts();
        } else {
            System.out.println("No savings accounts registered!");
        }
        return null;
    }

}

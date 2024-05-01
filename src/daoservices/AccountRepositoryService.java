package daoservices;

import dao.*;
import model.card.*;
import model.account.*;
import model.user.*;
import utils.*;

import java.sql.SQLException;
import java.util.List;

public class AccountRepositoryService {

    private AccountDAO accountDAO = AccountDAO.getInstance();
    private SavingsAccountDAO savingsAccountDAO = SavingsAccountDAO.getInstance();

    public AccountRepositoryService() throws SQLException {}


    public List<Account> getAccounts() throws SQLException{
        List<Account> accounts = accountDAO.read();
        if (!accounts.isEmpty()) {
            for (Account account : accounts)
                System.out.println(account.getName() + " : " + account.getIBAN());
        } else {
            System.out.println("No accounts registered!");
        }

        return accounts;
    }

    public List<SavingsAccount> getSavingsAccounts() throws SQLException{
        List<SavingsAccount> savingsAccounts = savingsAccountDAO.read();
        if (!savingsAccounts.isEmpty()) {
            for (SavingsAccount account : savingsAccounts)
                System.out.println(account.getName() + " : " + account.getIBAN());
        } else {
            System.out.println("No savings accounts registered!");
        }

        return savingsAccounts;
    }

    public void removeAccount(String typeOfAccount, String IBAN) throws SQLException {
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

    public void addAccount(Account account) throws SQLException{
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

    public Account getAccountByType(String typeOfAccount, String IBAN) throws SQLException{
        Account account = null;
        try {
            if (typeOfAccount.toLowerCase().equals("account")) {
                account = getAccountByIBAN(IBAN);
            } else if (typeOfAccount.toLowerCase().equals("savingsaccount")) {
                account = getSavingsAccountByIBAN(IBAN);
            }
            if (account == null) {
                System.out.println("No account having IBAN: " + IBAN);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return account;
    }

    public Account getAccountByIBAN(String IBAN) throws SQLException {
        List<Account> accounts = getAccounts();
        for (Account account : accounts)
            if (account.getIBAN().equals(IBAN))
                return account;
        return null;
    }

    public SavingsAccount getSavingsAccountByIBAN(String IBAN) throws SQLException {
        List<SavingsAccount> accounts = getSavingsAccounts();
        for (SavingsAccount account : accounts)
                if (account.getIBAN().equals(IBAN))
                    return account;
        return null;
    }

    public void updateAccount(Account account) throws SQLException {
        if (account != null) {
            switch (account.getClass().getSimpleName()){
                case "Account" -> accountDAO.update((Account) account);
                case "SavingsAccount" -> savingsAccountDAO.update((SavingsAccount) account);
                default -> throw new IllegalStateException("Unexpected value: " + account);
            }
        }
    }

}

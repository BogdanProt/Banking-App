package dao;

import model.account.*;
import java.util.ArrayList;
import java.util.List;

public class SavingsAccountDAO {

    private static List<SavingsAccount> savingsAccounts = new ArrayList<>();

    public SavingsAccount read(int userID) {
        if (!savingsAccounts.isEmpty()) {
            for (SavingsAccount sacc : savingsAccounts) {
                if (sacc.getUserID() == userID) {
                    return sacc;
                }
            }
        }
        return null;
    }

    public void delete(SavingsAccount savingsAccount) {
        savingsAccounts.remove(savingsAccount);
    }

    public void create(SavingsAccount savingsAccount) {
        savingsAccounts.add(savingsAccount);
    }
}

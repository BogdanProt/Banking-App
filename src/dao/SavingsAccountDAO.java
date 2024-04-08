package dao;

import model.account.*;
import java.util.ArrayList;
import java.util.List;

public class SavingsAccountDAO {

    private static List<SavingsAccount> savingsAccounts = new ArrayList<>();

    public SavingsAccount read(String IBAN) {
        if (!savingsAccounts.isEmpty()) {
            for (SavingsAccount sacc : savingsAccounts) {
                if (sacc.getIBAN().equals(IBAN)) {
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

    public List<SavingsAccount> getSavingsAccounts() { return savingsAccounts; }
}

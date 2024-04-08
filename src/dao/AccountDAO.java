package dao;

import model.account.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {

    private static List<Account> accounts = new ArrayList<>();

    public Account read(String IBAN) {
        if (!accounts.isEmpty()){
            for(Account acc : accounts) {
                if (acc.getIBAN().equals(IBAN)) {
                    return acc;
                }
            }
        }
        return null;
    }

    public void delete(Account account) {
        accounts.remove(account);
    }

    public void create(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() { return accounts; }
}

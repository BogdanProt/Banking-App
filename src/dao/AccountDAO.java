package dao;

import model.account.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {

    private static List<Account> accounts = new ArrayList<>();

    public Account read(int userID) {
        if (!accounts.isEmpty()){
            for(Account acc : accounts) {
                if (acc.getUserID() == userID) {
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

    public int getSize() { return accounts.size(); }
}

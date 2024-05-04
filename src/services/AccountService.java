package services;

import java.util.*;
import model.account.*;
import utils.*;
import daoservices.AccountRepositoryService;
import java.sql.*;

public class AccountService {
    private AccountRepositoryService databaseService;

    public AccountService() throws SQLException {
        this.databaseService = new AccountRepositoryService();
    }


}

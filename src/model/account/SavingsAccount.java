package model.account;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SavingsAccount extends Account{
    private final Date startDate, endDate;
    private final int interestRate;

    public SavingsAccount(String name, int userID, int uniqueID) {
        super(name, userID, uniqueID);

        this.startDate = new Date();
        this.interestRate = 4;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 3);
        this.endDate = calendar.getTime();
    }

    public SavingsAccount(String IBAN, String swift, double balance, String name, int userID, Date startDate, Date endDate, int interestRate) {
        super(IBAN, swift, balance, name, userID);

        this.startDate = startDate;
        this.endDate = endDate;
        this.interestRate = interestRate;
    }

    public Date getStartDate() { return startDate; }

    public Date getEndDate() { return endDate; }

    public int getInterestRate() { return interestRate; }


    @Override
    public String toString() {
        return "SavingsAccount{" +
                "IBAN='" + IBAN + '\'' +
                ", swift='" + swift + '\'' +
                ", amount=" + balance +
                ", name='" + name + '\'' +
                ", customerId=" + userID +
                ", cards=" + cards +
                ", startDate=" + (new SimpleDateFormat("yyyy-MM-dd")).format(startDate) +
                ", endDate=" + (new SimpleDateFormat("yyyy-MM-dd")).format(endDate) +
                ", interest=" + interestRate +
                '}';
    }

}

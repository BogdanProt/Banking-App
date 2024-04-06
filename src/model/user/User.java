package model.user;

import utils.*;
import model.card.*;
import model.account.*;
import java.util.*;
import java.text.ParseException;


public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String CNP;
    private String emailAddress;
    private String phoneNumber;
    private Address address;

    public User(int userID, String firstName, String lastName, String CNP, String emailAddress, String phoneNumber, Address address) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }


    // Setters
    public void setUserID(int userID) { this.userID = userID; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setCNP(String CNP) { this.CNP = CNP; }

    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setAddress(Address address) { this.address = address; }

    // Getters

    public int getUserID() { return userID; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getCNP() { return CNP; }

    public String getEmailAddress() { return emailAddress; }

    public String getPhoneNumber() { return phoneNumber; }

    public Address getAddress() { return address; }


    @Override
    public String toString() {
        return "{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", CNP='" + CNP + '\'' +
                ", email='" + emailAddress + '\'' +
                ", phone='" + phoneNumber + '\'' +
                ", address=" + address.toString() +
                '}';
    }
}

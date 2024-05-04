package model.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

public class Address {
    private String country;
    private String county;
    private String city;
    private String street;
    private int postalCode;

    public Address(String country, String county, String city, String street, int postalCode){
        this.country = country;
        this.county = county;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    public Address(Scanner scanner) {
        System.out.println("Country: ");
        country = scanner.nextLine().toLowerCase();
        System.out.println("County: ");
        county = scanner.nextLine().toLowerCase();
        System.out.println("City: ");
        city = scanner.nextLine().toLowerCase();
        System.out.println("Street: ");
        street = scanner.nextLine().toLowerCase();
        System.out.println("Postal code: ");
        postalCode = scanner.nextInt();
    }

    public Address(ResultSet in) throws SQLException {
        this.country = in.getString("country");
        this.county = in.getString("county");
        this.city = in.getString("city");
        this.street = in.getString("street");
        this.postalCode = in.getInt("postalCode");
    }

    public String getCountry() { return country; }

    public String getCounty() {
        return county;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setCity(String city) { this.city = city; }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "{" +
                "country='" + country + '\'' +
                ", county='" + county + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postalCode=" + postalCode +
                '}';
    }
}

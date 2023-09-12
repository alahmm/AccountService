package crudexercices.businesslayout;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private LocalDate registrationDate;
    private LocalDate subscriptionEndsOn;
    private boolean isSubscriptionActive;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public LocalDate getSubscriptionEndsOn() {
        return subscriptionEndsOn;
    }

    public boolean isSubscriptionActive() {
        return isSubscriptionActive;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setSubscriptionEndsOn(LocalDate subscriptionEndsOn) {
        this.subscriptionEndsOn = subscriptionEndsOn;
    }

    public void setSubscriptionActive(boolean subscriptionActive) {
        isSubscriptionActive = subscriptionActive;
    }

    public Customer() {

    }

    public Customer(long id, String name, String surname, String phoneNumber, LocalDate registrationDate, LocalDate subscriptionEndsOn, boolean isSubscriptionActive) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.subscriptionEndsOn = subscriptionEndsOn;
        this.isSubscriptionActive = isSubscriptionActive;
    }
    // constructor, getters, setters
}

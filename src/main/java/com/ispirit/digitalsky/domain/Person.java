package com.ispirit.digitalsky.domain;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "DS_PERSON")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "AGE_IN_MONTHS")
    private int ageInMonths;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PILOT_ID")
    private List<PersonAddress> addressList;

    private Person() {

    }

    public Person(String firstName, String lastName, String email, String mobileNumber, String country, int ageInMonths, List<PersonAddress> addressList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.country = country;
        this.ageInMonths = ageInMonths;
        this.addressList = addressList;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getCountry() {
        return country;
    }

    public int getAgeInMonths() {
        return ageInMonths;
    }

    public List<PersonAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<PersonAddress> addressList) {
        this.addressList = addressList;
    }
}
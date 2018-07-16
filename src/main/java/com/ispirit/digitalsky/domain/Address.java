package com.ispirit.digitalsky.domain;


import javax.persistence.*;

@Entity
@Table(name = "DS_ADDRESS")
public class Address {

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "LINE_ONE")
    private String lineOne;

    @Column(name = "LINE_TWO")
    private String lineTwo;

    @Column(name = "TOWN_OR_CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "PIN_CODE")
    private String pincode;

    private Address() {
    }

    public Address(String lineOne, String lineTwo, String city, String state, String country, String pincode) {
        this.lineOne = lineOne;
        this.lineTwo = lineTwo;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }

    public long getId() {
        return id;
    }

    public String getLineOne() {
        return lineOne;
    }

    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

}

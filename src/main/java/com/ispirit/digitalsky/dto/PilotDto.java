package com.ispirit.digitalsky.dto;

import com.ispirit.digitalsky.domain.Pilot;

import java.util.List;

public class PilotDto {

    private String name;

    private String phoneNumber;

    private int ageInMonths;

    private String country;

    private List<AddressDto> addressList;

    private PilotDto() {
        //for serialization and de-serialization
    }

    public PilotDto(String name, String phoneNumber, int ageInMonths, String country, List<AddressDto> addressList) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.ageInMonths = ageInMonths;
        this.country = country;
        this.addressList = addressList;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAgeInMonths() {
        return ageInMonths;
    }

    public String getCountry() {
        return country;
    }

    public List<AddressDto> getAddressList() {
        return addressList;
    }


    public Pilot build(long resourceOwnerId) {
        return null;
    }
}


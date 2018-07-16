package com.ispirit.digitalsky.dto;

public class AddressDto {

    private String addressLineOne;

    private String addressLineTwo;

    private String cityOrTown;

    private String state;

    private String country;

    private String type;

    private AddressDto() {
        //for serialization and de-serialization
    }

    public AddressDto(String addressLineOne, String addressLineTwo, String cityOrTown, String state, String country, String type) {
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.cityOrTown = cityOrTown;
        this.state = state;
        this.country = country;
        this.type = type;
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public String getCityOrTown() {
        return cityOrTown;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getType() {
        return type;
    }
}

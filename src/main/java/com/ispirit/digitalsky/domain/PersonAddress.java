package com.ispirit.digitalsky.domain;

import javax.persistence.*;

@Entity
@Table(name = "DS_PERSONA_ADDRESS")
public class PersonAddress {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "TYPE")
    private String type;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private PersonAddress() {
    }

    public PersonAddress(String type, Address address) {
        this.type = type;
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public Address getAddress() {
        return address;
    }
}

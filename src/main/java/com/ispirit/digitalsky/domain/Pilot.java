package com.ispirit.digitalsky.domain;


import javax.persistence.*;

@Entity
@Table(name = "DS_PERSON")
public class Pilot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "RESOURCE_OWNER_ID")
    private long resourceOwnerId;


    @Column(name = "STATUS")
    private String status;


    @OneToOne(cascade = CascadeType.ALL)
    private Person person;


    private Pilot() {
        //for serialization and de-serialization
    }

    public Pilot(long id, long resourceOwnerId, String status, Person person) {
        this.id = id;
        this.resourceOwnerId = resourceOwnerId;
        this.status = status;
        this.person = person;
    }

    public Pilot(long resourceOwnerId, String status, Person person) {
        this.id = id;
        this.resourceOwnerId = resourceOwnerId;
        this.status = status;
        this.person = person;
    }

    public long getId() {
        return id;
    }

    public long getResourceOwnerId() {
        return resourceOwnerId;
    }

    public String getStatus() {
        return status;
    }

    public Person getPerson() {
        return person;
    }
}

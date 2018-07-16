package com.ispirit.digitalsky.repository;

import com.ispirit.digitalsky.domain.Address;
import com.ispirit.digitalsky.domain.Person;
import com.ispirit.digitalsky.domain.PersonAddress;
import com.ispirit.digitalsky.domain.Pilot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PilotRepositoryTest {

    @Autowired
    PilotRepository pilotRepository;

    @Test
    public void savePilotDetails() throws Exception {
        Address addressHome = new Address("line1","line2","ml","bangalore","india","560001");
        Address addressTwo = new Address("line12","line22","ml","bangalore","india","560001");
        Address addressHome1 = new Address("line111","line2","ml","bangalore","india","560001");
        Address addressTwo2 = new Address("line12343","line22","ml","bangalore","india","560001");
        Person person = new Person("fName","lName","email","mobile","India",500, toAddressList(new PersonAddress("HOME", addressHome), new PersonAddress("OFFICE",addressTwo)));
        Pilot pilot = new Pilot(1L,null, person);
        Pilot savedPilot = pilotRepository.save(pilot);

        Pilot pilot1 = pilotRepository.findOne(Long.valueOf(savedPilot.getId()));


        pilot1.getPerson().setAddressList(toAddressList(new PersonAddress("HOME",addressHome1),new PersonAddress("HOME",addressTwo2)));

        pilotRepository.save(pilot1);
        System.out.println("x");
    }

    List<PersonAddress> toAddressList(PersonAddress... addresses){
        ArrayList<PersonAddress> addresses1 = new ArrayList<>();
        Collections.addAll(addresses1, addresses);
        return addresses1;

    }
}
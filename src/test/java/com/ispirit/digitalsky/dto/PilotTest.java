package com.ispirit.digitalsky.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Arrays;

public class PilotTest {

    @Test
    public void shouldSerialize() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AddressDto addressDtoHome = new AddressDto("line1","line2","Indiranagar","Bangalore","560001", "HOME");
        AddressDto addressDtoOffice = new AddressDto("line1","line2","Indiranagar","Bangalore","560001", "OFFICE");
        PilotDto value = new PilotDto("name","phone",25,"India", Arrays.asList(addressDtoHome, addressDtoOffice));
        String asString = objectMapper.writeValueAsString(value);
        PilotDto pilotDto = objectMapper.readValue(asString, PilotDto.class);
        System.out.println(asString);
    }
}
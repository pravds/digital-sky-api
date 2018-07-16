package com.ispirit.digitalsky.controller;

import com.ispirit.digitalsky.domain.Pilot;
import com.ispirit.digitalsky.domain.UserPrincipal;
import com.ispirit.digitalsky.dto.*;
import com.ispirit.digitalsky.exception.EntityNotFoundException;
import com.ispirit.digitalsky.service.api.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.ispirit.digitalsky.controller.PilotController.PILOT_RESOURCE_BASE_PATH;
import static com.ispirit.digitalsky.controller.UserController.USER_RESOURCE_BASE_PATH;
import static java.util.Collections.emptyMap;
import static org.springframework.util.StringUtils.containsWhitespace;

@RestController
@RequestMapping(PILOT_RESOURCE_BASE_PATH)
public class PilotController {

    public static final String PILOT_RESOURCE_BASE_PATH = "/api/pilot";

    @Autowired
    private PilotService pilotService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody PilotDto pilotDtoPayload) {

        if (!validate(pilotDtoPayload)) {
            return new ResponseEntity<>(new Errors("Invalid Payload"), HttpStatus.BAD_REQUEST);
        }

        UserPrincipal userPrincipal = UserPrincipal.securityContext();

        Pilot pilot= pilotDtoPayload.build(userPrincipal.getId());

        pilotService.createNewPilot(pilot);

        return new ResponseEntity<>(emptyMap(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> get(@PathVariable("id") long id) {
        Pilot pilot = pilotService.find(id);
        if (pilot != null) {
            return new ResponseEntity<>(pilot, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Errors("Pilot details not found"), HttpStatus.NOT_FOUND);
        }
    }


    private boolean validate(PilotDto pilotDtoPayload) {
        return true;
    }

}

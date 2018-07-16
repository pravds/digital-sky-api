package com.ispirit.digitalsky.service.api;


import com.ispirit.digitalsky.domain.Pilot;

public interface PilotService {

    void createNewPilot(Pilot pilot);

    Pilot find(long id);
}

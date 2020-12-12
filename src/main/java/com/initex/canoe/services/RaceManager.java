package com.initex.canoe.services;

import com.initex.canoe.domain.EventRegistry;
import com.initex.canoe.domain.Race;
import com.initex.canoe.domain.SubEvent;
import com.initex.canoe.dto.McuDataDTO;
import com.initex.canoe.dto.RaceDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class RaceManager {

    private EventRegistryService eventRegistryService;
    private RaceService raceService;


    @Autowired
    public RaceManager(EventRegistryService eventRegistryService, RaceService raceService) {
        this.eventRegistryService = eventRegistryService;
        this.raceService = raceService;
    }

    public void processResult(McuDataDTO mcuDataDTO) throws NotFoundException {
        final EventRegistry eventRegistry = eventRegistryService.findRecord(
                mcuDataDTO.getEventId(),
                mcuDataDTO.getBib(),
                mcuDataDTO.isTeamMode());
        if (eventRegistry != null) {
            processRaceData(eventRegistry, mcuDataDTO);
        } else {
            throw new NotFoundException("No competitor registry found!");
        }
    }

    private void processRaceData(final EventRegistry eventRegistry, final McuDataDTO mcuDataDTO) {
        final SubEvent subEvent = new SubEvent();
        subEvent.setId((long) mcuDataDTO.getSubEventId());
        final Race race = raceService.findByEventRegistryAndSubEvent(eventRegistry, subEvent);
        if (!isEmpty(race)) {
            processExistingRecord(mcuDataDTO, race);
        } else {
            processNewRecord(eventRegistry, mcuDataDTO, subEvent);
        }
    }

    private void processExistingRecord(McuDataDTO mcuDataDTO, Race race) {
        if (mcuDataDTO.getUnitId() == 1) {
            race.setStartTime(mcuDataDTO.getTimeStamp());
        } else {
            race.setFinishTime(mcuDataDTO.getTimeStamp());
        }
        raceService.save(race);
    }

    private void processNewRecord(EventRegistry eventRegistry, McuDataDTO mcuDataDTO, SubEvent subEvent) {
        final RaceDTO raceDTO = new RaceDTO();
        raceDTO.setEventRegistry(eventRegistry);
        raceDTO.setEvent(eventRegistry.getEvent());
        raceDTO.setSubEvent(subEvent);
        raceDTO.setTeamMode(mcuDataDTO.isTeamMode());
        if (mcuDataDTO.getUnitId() == 1) {
            raceDTO.setStartTime(mcuDataDTO.getTimeStamp());
        } else {
            raceDTO.setFinishTime(mcuDataDTO.getTimeStamp());
        }
        raceService.addRace(raceDTO);
    }
}

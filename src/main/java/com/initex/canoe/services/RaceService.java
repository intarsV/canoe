package com.initex.canoe.services;

import com.initex.canoe.domain.EventRegistry;
import com.initex.canoe.domain.Race;
import com.initex.canoe.domain.SubEvent;
import com.initex.canoe.dto.RaceDTO;
import com.initex.canoe.dto.RaceRequestParamsDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.repository.RaceRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.initex.canoe.services.utils.Constants.*;

@Service
public class RaceService {

    private RaceRepository repository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public RaceService(RaceRepository repository) {
        this.repository = repository;
    }

    public List<RaceDTO> getRace(final RaceRequestParamsDTO params) {
        try {
            final List<Race> initialData = repository.findByEventIdAndSubEventIdAndDoneAndTeamModeAndDisabled(
                    params.getEventId(), params.getSubEventId(), params.isDone(), params.isTeamMode(), params.isDisabled());
            return initialData.stream().map(item -> mapper.map(item, RaceDTO.class)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new CanoeException(DATABASE_READ_ERROR, e);
        }
    }

    public List<Race> getAllRaceData(long eventId) {
        try {
            return repository.findByEventIdAndDoneAndDisabled(eventId, true, false);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_READ_ERROR, e);
        }
    }

    public RaceDTO addRace(final RaceDTO raceDTO) {
        try {
            final Race race = mapper.map(raceDTO, Race.class);
            raceDTO.setId(repository.save(race).getId());
            return raceDTO;
        } catch (DataIntegrityViolationException e) {
            throw new CanoeException(DUPLICATE_DATA_EXISTS);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_SAVE_ERROR, e);
        }
    }

    public RaceDTO updateRace(final String id, final RaceDTO raceDTO) {
        try {
            final int result = repository.saveIfExists(raceDTO, Long.valueOf(id));
            if (result == 0) {
                throw new NotFoundException(NO_SUCH_RECORD_ERROR);
            }
            raceDTO.setId(Long.valueOf(id));
            return raceDTO;
        } catch (NotFoundException e) {
            throw new CanoeException(NO_SUCH_RECORD_ERROR);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_UPDATE_ERROR, e);
        }
    }

    public Race findByEventRegistryAndSubEvent(EventRegistry eventRegistry, SubEvent subEvent) {
        return repository.findByEventRegistryAndSubEvent(eventRegistry, subEvent);
    }

    public void save(Race race) {
        try {
            repository.save(race);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_SAVE_ERROR, e);
        }
    }
}

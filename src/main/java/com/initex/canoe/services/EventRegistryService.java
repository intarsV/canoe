package com.initex.canoe.services;

import com.initex.canoe.domain.EventRegistry;
import com.initex.canoe.dto.EventRegistryDTO;
import com.initex.canoe.dto.EventRegistryQuery;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.repository.EventRegistryRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.initex.canoe.services.utils.Constants.*;

@Service
public class EventRegistryService {

    private EventRegistryRepository repository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public EventRegistryService(EventRegistryRepository repository) {
        this.repository = repository;
    }

    public List<EventRegistry> getEventRegistry(final EventRegistryQuery query) {
        try {
            return repository.findAllByEventIdAndTeamModeAndDisabled(query.getEventId(), query.isTeamMode(), false);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_READ_ERROR, e);
        }
    }

    public EventRegistryDTO addEventRegistry(final EventRegistryDTO eventRegistryDTO) {
        try {
            final EventRegistry eventRegistry = mapper.map(eventRegistryDTO, EventRegistry.class);
            eventRegistryDTO.setId(repository.save(eventRegistry).getId());
            return eventRegistryDTO;
        } catch (DataIntegrityViolationException e) {
            throw new CanoeException(DUPLICATE_DATA_EXISTS);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_SAVE_ERROR, e);
        }
    }

    public EventRegistryDTO updateEventRegistry(final String id, final EventRegistryDTO eventRegistryDTO) {
        try {
            final int result = repository.saveIfExists(eventRegistryDTO, Long.valueOf(id));
            if (result == 0) {
                throw new NotFoundException(NO_SUCH_RECORD_ERROR);
            }
            eventRegistryDTO.setId(Long.valueOf(id));
            return eventRegistryDTO;
        } catch (NotFoundException e) {
            throw new CanoeException(NO_SUCH_RECORD_ERROR);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_UPDATE_ERROR, e);
        }
    }

    public EventRegistry findRecord(long eventId, int bib, boolean isTeamMode) {
        return repository.findByEventIdAndBibAndTeamModeAndDisabled(eventId, bib, isTeamMode, false);
    }

    public List<EventRegistry> findByParameters(EventRegistryQuery query) {
        return repository.findByParameters(query);
    }
}

package com.initex.canoe.services;

import com.initex.canoe.domain.Event;
import com.initex.canoe.dto.EventDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.repository.EventRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.initex.canoe.services.utils.Constants.*;

@Service
public class EventService {

    private EventRepository repository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public List<Event> getEvents() {
        try {
            return repository.findAllByDisabled(false);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_READ_ERROR, e);
        }
    }

    public EventDTO addEvent(final EventDTO eventDTO) {
        try {
                final Event event = mapper.map(eventDTO, Event.class);
                eventDTO.setId(repository.save(event).getId());
                return eventDTO;
        } catch (DataIntegrityViolationException e) {
            throw new CanoeException(DUPLICATE_DATA_EXISTS);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_SAVE_ERROR, e);
        }
    }

    public EventDTO updateEvent(final String id, final EventDTO eventDTO) {
        try {
            final int result = repository.updateIfExists(eventDTO, Long.valueOf(id));
            if (result == 0) {
                throw new NotFoundException(NO_SUCH_RECORD_ERROR);
            }
            eventDTO.setId(Long.valueOf(id));
            return eventDTO;
        } catch (NotFoundException e) {
            throw new CanoeException(NO_SUCH_RECORD_ERROR);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_UPDATE_ERROR, e);
        }
    }
}
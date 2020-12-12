package com.initex.canoe.services;

import com.initex.canoe.domain.EventFormat;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.repository.EventFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.initex.canoe.services.utils.Constants.DATABASE_READ_ERROR;

@Service
public class EventFormatService {

    private EventFormatRepository repository;

    @Autowired
    public EventFormatService(EventFormatRepository repository) {
        this.repository = repository;
    }

    public List<EventFormat> getEventFormats() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new CanoeException(DATABASE_READ_ERROR, e);
        }
    }
}

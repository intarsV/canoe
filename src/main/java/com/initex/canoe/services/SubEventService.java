package com.initex.canoe.services;

import com.initex.canoe.domain.SubEvent;
import com.initex.canoe.dto.SubEventDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.repository.SubEventRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.initex.canoe.services.utils.Constants.*;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class SubEventService {

    private SubEventRepository repository;
    private ModelMapper mapper = new ModelMapper();
    private Logger logger = getLogger(getClass());

    @Autowired
    public SubEventService(SubEventRepository repository) {
        this.repository = repository;
    }

    public List<SubEvent> getSubEvent() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new CanoeException(DATABASE_READ_ERROR, e);
        }
    }

    public SubEventDTO addSubEvent(final SubEventDTO subEventDTO) {
        try {
            if (!repository.existsBySubEventAndEventFormat(subEventDTO.getSubEvent(), subEventDTO.getEventFormat())) {
                final SubEvent subEvent = mapper.map(subEventDTO, SubEvent.class);
                subEventDTO.setId(repository.save(subEvent).getId());
                return subEventDTO;
            } else {
                logger.error("Duplicate data exists!");
                throw new CanoeException(DUPLICATE_DATA_EXISTS);
            }
        } catch (Exception e) {
            throw new CanoeException(DATABASE_SAVE_ERROR, e);
        }
    }

    public SubEventDTO updateSubEvent(final String id, final SubEventDTO subEventDTO) {
        try {
            if (repository.existsById(Long.valueOf(id))) {
                final SubEvent subEvent = mapper.map(subEventDTO, SubEvent.class);
                subEvent.setId(Long.valueOf(id));
                subEventDTO.setId(repository.save(subEvent).getId());
                return subEventDTO;
            } else {
                logger.error(NO_SUCH_RECORD_ERROR);
                throw new CanoeException(NO_SUCH_RECORD_ERROR);
            }

        } catch (Exception e) {
            throw new CanoeException(DATABASE_UPDATE_ERROR, e);
        }
    }
}
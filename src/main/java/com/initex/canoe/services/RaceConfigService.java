package com.initex.canoe.services;

import com.initex.canoe.domain.RaceConfig;
import com.initex.canoe.dto.RaceConfigDTO;
import com.initex.canoe.dto.RaceConfigQuery;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.repository.RaceConfigRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.initex.canoe.services.utils.Constants.*;

@Service
public class RaceConfigService {

    private RaceConfigRepository repository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public RaceConfigService(RaceConfigRepository repository) {
        this.repository = repository;
    }

    public List<RaceConfig> getList(final RaceConfigQuery query) {
        try {
            return repository.getAllByEventId(query.getEventId());
        } catch (Exception e) {
            throw new CanoeException(DATABASE_READ_ERROR, e);
        }
    }

    public RaceConfigDTO addRaceConfig(final RaceConfigDTO raceConfigDTO) {
        try {
            final RaceConfig raceConfig = mapper.map(raceConfigDTO, RaceConfig.class);
            raceConfigDTO.setId(repository.save(raceConfig).getId());
            return raceConfigDTO;
        } catch (DataIntegrityViolationException e) {
            throw new CanoeException(DUPLICATE_DATA_EXISTS);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_SAVE_ERROR, e);
        }
    }

    public RaceConfigDTO updateRaceConfig(final String id, final RaceConfigDTO raceConfigDTO) {
        try {
            final int result = repository.saveIfExists(raceConfigDTO, Long.valueOf(id));
            if (result == 0) {
                throw new NotFoundException(NO_SUCH_RECORD_ERROR);
            }
            raceConfigDTO.setId(Long.valueOf(id));
            return raceConfigDTO;
        } catch (NotFoundException e) {
            throw new CanoeException(NO_SUCH_RECORD_ERROR);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_UPDATE_ERROR, e);
        }
    }
}

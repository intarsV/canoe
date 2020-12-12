package com.initex.canoe.services;

import com.initex.canoe.domain.McuData;
import com.initex.canoe.dto.McuDataDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.repository.McuDataRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.initex.canoe.services.utils.Constants.*;

@Service
public class McuDataService {

    private McuDataRepository repository;
    private ModelMapper mapper = new ModelMapper();
    private RaceManager raceManager;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public McuDataService(McuDataRepository repository, RaceManager raceManager, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.raceManager = raceManager;
        this.eventPublisher = eventPublisher;
    }

    public List<McuDataDTO> getMcuData(final boolean isDone) {
        try {
            List<McuData> initialData = repository.findByDoneAndDisabled(isDone, false);
            return initialData.stream().map(item -> mapper.map(item, McuDataDTO.class)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new CanoeException(DATABASE_READ_ERROR, e);
        }
    }

    public McuDataDTO addMcuData(final McuDataDTO mcuDataDTO) {
        try {
            final McuData mcuData = mapper.map(mcuDataDTO, McuData.class);
            mcuDataDTO.setId(repository.save(mcuData).getId());
            eventPublisher.publishEvent(mcuDataDTO);
            return mcuDataDTO;
        } catch (DataIntegrityViolationException e) {
            throw new CanoeException(DUPLICATE_DATA_EXISTS);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_SAVE_ERROR, e);
        }
    }

    public McuDataDTO updateMcuData(final String id, final McuDataDTO mcuDataDTO) {
        try {
            raceManager.processResult(mcuDataDTO);
            final int result = repository.saveIfExists(mcuDataDTO.getBib(),
                    mcuDataDTO.getTimeStamp(),
                    mcuDataDTO.getUnitId(),
                    mcuDataDTO.getSubEventId(),
                    mcuDataDTO.isDone(),
                    mcuDataDTO.isDisabled(),
                    Long.valueOf(id));
            if (result == 0) {
                throw new NotFoundException(NO_SUCH_RECORD_ERROR);
            }
            mcuDataDTO.setId(Long.valueOf(id));
            return mcuDataDTO;
        } catch (NotFoundException e) {
            throw new CanoeException(NO_SUCH_RECORD_ERROR);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_UPDATE_ERROR, e);
        }
    }
}

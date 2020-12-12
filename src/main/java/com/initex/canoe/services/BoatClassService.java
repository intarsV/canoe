package com.initex.canoe.services;

import com.initex.canoe.domain.BoatClass;
import com.initex.canoe.dto.BoatClassDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.repository.BoatClassRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.initex.canoe.services.utils.Constants.*;

@Service
public class BoatClassService {

    private BoatClassRepository repository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public BoatClassService(BoatClassRepository repository) {
        this.repository = repository;
    }

    public List<BoatClass> getBoatClass() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new CanoeException(DATABASE_READ_ERROR, e);
        }
    }

    public BoatClassDTO addBoatClass(final BoatClassDTO boatClassDTO) {
        try {
            final BoatClass boatClass = mapper.map(boatClassDTO, BoatClass.class);
            boatClassDTO.setId(repository.save(boatClass).getId());
            return boatClassDTO;
        } catch (DataIntegrityViolationException e) {
            throw new CanoeException(DUPLICATE_DATA_EXISTS);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_SAVE_ERROR, e);
        }
    }

    public BoatClassDTO updateBoatClass(final String id, final BoatClassDTO boatClassDTO) {
        try {
            final int result = repository.updateIfExists(boatClassDTO.getBoatClass(), Long.valueOf(id));
            if (result == 0) {
                throw new NotFoundException(NO_SUCH_RECORD_ERROR);
            }
            boatClassDTO.setId(Long.valueOf(id));
            return boatClassDTO;
        } catch (NotFoundException e) {
            throw new CanoeException(NO_SUCH_RECORD_ERROR);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_UPDATE_ERROR, e);
        }
    }
}

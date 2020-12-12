package com.initex.canoe.services;

import com.initex.canoe.domain.AgeGroup;
import com.initex.canoe.dto.AgeGroupDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.repository.GroupRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.initex.canoe.services.utils.Constants.*;

@Service
public class AgeGroupService {

    private GroupRepository repository;
    private ModelMapper mapper = new ModelMapper();

    public AgeGroupService(GroupRepository repository) {
        this.repository = repository;
    }

    public List<AgeGroup> getGroups() {
        try {
            return repository.findAllByDisabled(false);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_READ_ERROR, e);
        }
    }

    public AgeGroupDTO addGroup(final AgeGroupDTO ageGroupDTO) {
        try {
            final AgeGroup ageGroup = mapper.map(ageGroupDTO, AgeGroup.class);
            ageGroupDTO.setId(repository.save(ageGroup).getId());
            return ageGroupDTO;
        } catch (DataIntegrityViolationException e) {
            throw new CanoeException(DUPLICATE_DATA_EXISTS);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_SAVE_ERROR, e);
        }
    }

    public AgeGroupDTO updateGroup(final String id, final AgeGroupDTO ageGroupDTO) {
        try {
            final int result = repository.updateIfExists(ageGroupDTO.getAgeGroup(), Long.valueOf(id));
            if (result == 0) {
                throw new NotFoundException(NO_SUCH_RECORD_ERROR);
            }
            ageGroupDTO.setId(Long.valueOf(id));
            return ageGroupDTO;
        } catch (NotFoundException e) {
            throw new CanoeException(NO_SUCH_RECORD_ERROR);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_UPDATE_ERROR, e);
        }
    }
}
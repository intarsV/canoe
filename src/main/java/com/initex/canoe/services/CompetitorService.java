package com.initex.canoe.services;

import com.initex.canoe.domain.Competitor;
import com.initex.canoe.dto.CompetitorDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.repository.CompetitorRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.initex.canoe.services.utils.Constants.*;

@Service
public class CompetitorService {

    private CompetitorRepository repository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public CompetitorService(CompetitorRepository repository) {
        this.repository = repository;
    }

    public List<Competitor> getCompetitors() {
        try {
            return repository.findAllByDisabled(false);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_READ_ERROR, e);
        }
    }

    public CompetitorDTO addCompetitor(final CompetitorDTO competitorDTO) {
        try {
            final Competitor competitor = mapper.map(competitorDTO, Competitor.class);
            competitorDTO.setId(repository.save(competitor).getId());
            return competitorDTO;
        } catch (DataIntegrityViolationException e) {
            throw new CanoeException(DUPLICATE_DATA_EXISTS);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_SAVE_ERROR, e);
        }
    }

    public CompetitorDTO updateCompetitor(final String id, final CompetitorDTO competitorDTO) {
        try {
            final int result = repository.updateIfExists(competitorDTO, Long.valueOf(id));
            if (result == 0) {
                throw new NotFoundException(NO_SUCH_RECORD_ERROR);
            }
            competitorDTO.setId(Long.valueOf(id));
            return competitorDTO;
        } catch (NotFoundException e) {
            throw new CanoeException(NO_SUCH_RECORD_ERROR);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_UPDATE_ERROR, e);
        }
    }
}

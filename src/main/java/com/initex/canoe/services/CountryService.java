package com.initex.canoe.services;

import com.initex.canoe.domain.Country;
import com.initex.canoe.dto.CountryDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.repository.CountryRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.initex.canoe.services.utils.Constants.*;

@Service
public class CountryService {

    private CountryRepository repository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    public List<CountryDTO> getCountryList() {
        try {
            List<Country> initialData = repository.findAll();
            return initialData.stream().map(item -> mapper.map(item, CountryDTO.class)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new CanoeException(DATABASE_READ_ERROR, e);
        }
    }

    public CountryDTO addCountry(final CountryDTO countryDTO) {
        try {
            final Country country = mapper.map(countryDTO, Country.class);
            countryDTO.setId(repository.save(country).getId());
            return countryDTO;
        } catch (DataIntegrityViolationException e) {
            throw new CanoeException(DUPLICATE_DATA_EXISTS);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_SAVE_ERROR, e);
        }
    }

    public CountryDTO updateCountry(final String id, final CountryDTO countryDTO) {
        try {
            final int result = repository.updateIfExists(countryDTO.getCountry(), Long.valueOf(id));
            if (result == 0) {
                throw new NotFoundException(NO_SUCH_RECORD_ERROR);
            }
            countryDTO.setId(Long.valueOf(id));
            return countryDTO;
        } catch (NotFoundException e) {
            throw new CanoeException(NO_SUCH_RECORD_ERROR);
        } catch (Exception e) {
            throw new CanoeException(DATABASE_UPDATE_ERROR, e);
        }
    }
}

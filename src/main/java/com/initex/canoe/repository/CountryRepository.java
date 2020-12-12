package com.initex.canoe.repository;

import com.initex.canoe.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    boolean existsByCountry(final String country);

    @Transactional
    @Modifying
    @Query("UPDATE Country SET country = :country WHERE id = :id")
    int updateIfExists(@Param("country") String country, @Param("id") Long id);
}

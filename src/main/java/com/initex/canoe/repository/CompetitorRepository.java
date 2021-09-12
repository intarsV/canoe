package com.initex.canoe.repository;

import com.initex.canoe.domain.Competitor;
import com.initex.canoe.dto.CompetitorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CompetitorRepository extends JpaRepository<Competitor, Long> {

    List<Competitor> findAllByDisabledOrderByCompetitorNameAsc(final boolean disabled);

    @Transactional
    @Modifying
    @Query(" UPDATE Competitor SET competitorName= :#{#c.competitorName}, birthYear= :#{#c.birthYear}, " +
            "club= :#{#c.club}, country= :#{#c.country}, disabled= :#{#c.disabled} WHERE id= :id ")
    int updateIfExists(@Param("c") CompetitorDTO c, @Param("id") Long id);
}

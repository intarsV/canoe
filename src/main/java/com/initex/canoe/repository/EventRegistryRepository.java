package com.initex.canoe.repository;

import com.initex.canoe.domain.*;
import com.initex.canoe.dto.EventRegistryDTO;
import com.initex.canoe.dto.EventRegistryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EventRegistryRepository extends JpaRepository<EventRegistry, Long> {
    List<EventRegistry>findAllByEventIdAndBoatClassId( long eventId, long boarClassId);

    List<EventRegistry> findAllByEventIdAndTeamModeAndDisabled(long eventId, boolean teamMode, boolean disabled);

    EventRegistry findByEventIdAndBibAndTeamModeAndDisabled(long eventId, int bib, boolean teamMode, boolean disabled);

    @Transactional
    @Modifying
    @Query("UPDATE EventRegistry SET event=:#{#er.event}, competitor= :#{#er.competitor}, teamMates= :#{#er.teamMates}," +
            " ageGroup= :#{#er.ageGroup}, boatClass= :#{#er.boatClass}, bib= :#{#er.bib}, teamMode= :#{#er.teamMode}," +
            " disabled= :#{#er.disabled} WHERE id= :id")
    int saveIfExists(@Param("er") EventRegistryDTO er, @Param("id") Long id);

    @Query("SELECT er FROM EventRegistry er WHERE er.event.id= :#{#q.eventId} AND  er.teamMode=:#{#q.teamMode} " +
            "AND (:#{#q.boatClassId} IS NULL OR er.boatClass.id=:#{#q.boatClassId})")
    List<EventRegistry> findByParameters(@Param("q") EventRegistryQuery q);

}
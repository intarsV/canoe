package com.initex.canoe.repository;

import com.initex.canoe.domain.EventRegistry;
import com.initex.canoe.domain.Race;
import com.initex.canoe.domain.SubEvent;
import com.initex.canoe.dto.RaceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

    List<Race> findByEventIdAndSubEventIdAndDoneAndTeamModeAndDisabled(
            Long eventId, Long subEventId,  boolean done,boolean teamMode, boolean disabled);

    List<Race> findByEventIdAndDoneAndDisabled(Long eventId, boolean done, boolean disabled);

    @Transactional
    @Modifying
    @Query("UPDATE Race SET startTime=:#{#r.startTime}, finishTime=:#{#r.finishTime}, g1=:#{#r.g1}, g2=:#{#r.g2}," +
            " g3=:#{#r.g3}, g4=:#{#r.g4}, g5=:#{#r.g5}, g6=:#{#r.g6}, g7=:#{#r.g7},  g8=:#{#r.g8},  g9=:#{#r.g9}," +
            " g10=:#{#r.g10}, g11=:#{#r.g11}, g12=:#{#r.g12}, g13=:#{#r.g13}, g14=:#{#r.g14}, g15=:#{#r.g15}," +
            " g16=:#{#r.g16}, g17=:#{#r.g17}, g18=:#{#r.g18}, g19=:#{#r.g19}, g20=:#{#r.g20}, g21=:#{#r.g21}," +
            " g22=:#{#r.g22}, g23=:#{#r.g23}, g24=:#{#r.g24}, eventRegistry=:#{#r.eventRegistry}," +
            " subEvent=:#{#r.subEvent}, dsqr=:#{#r.dsqr}, done=:#{#r.done}, disabled=:#{#r.disabled} where id=:id")
    int saveIfExists(@Param("r") RaceDTO r, @Param("id") Long id);

    Race findByEventRegistryAndSubEvent(EventRegistry eventRegistry, SubEvent subEvent);
}

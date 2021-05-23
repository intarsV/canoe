package com.initex.canoe.repository;

import com.initex.canoe.domain.RaceConfig;
import com.initex.canoe.dto.RaceConfigDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RaceConfigRepository extends JpaRepository<RaceConfig, Long> {

    List<RaceConfig> getAllByEventId(long eventId);

    @Transactional
    @Modifying
    @Query("UPDATE RaceConfig SET id=:id, event=:#{#r.event}, boatClass=:#{#r.boatClass}, heat1=:#{#r.heat1}, heat2=:#{#r.heat2}, semiFinal=:#{#r.semiFinal}  WHERE id = :id")
    int saveIfExists(@Param("r") RaceConfigDTO r, @Param("id") Long id);

}

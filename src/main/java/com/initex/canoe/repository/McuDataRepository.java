package com.initex.canoe.repository;

import com.initex.canoe.domain.McuData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface McuDataRepository extends JpaRepository<McuData, Long> {

    List<McuData> findByDoneAndDisabled(boolean isDone, boolean disabled);

    @Transactional
    @Modifying
    @Query(" UPDATE McuData SET bib= :bib, timeStamp= :timeStamp, unitId= :unitId, subEvent= :subEvent, done= :done, disabled= :disabled WHERE id= :id ")
    int saveIfExists(@Param("bib") int bib,
                     @Param("timeStamp") long timeStamp,
                     @Param("unitId") int unitId,
                     @Param("subEvent") int subEvent,
                     @Param("done") boolean done,
                     @Param("disabled") boolean disabled,
                     @Param("id") Long id);
}

package com.initex.canoe.repository;

import com.initex.canoe.domain.Event;
import com.initex.canoe.dto.EventDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByDisabled(boolean disabled);

    boolean existsByEventName(String eventName);

    @Transactional
    @Modifying
    @Query(" UPDATE Event SET eventName= :#{#e.eventName}, eventFormat= :#{#e.eventFormat}, " +
            "placeDate= :#{#e.placeDate}, disabled= :#{#e.disabled} WHERE id= :id ")
    int updateIfExists(@Param("e") EventDTO e, @Param("id") Long id);
}

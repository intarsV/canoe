package com.initex.canoe.repository;

import com.initex.canoe.domain.EventFormat;
import com.initex.canoe.domain.SubEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubEventRepository extends JpaRepository<SubEvent, Long> {

    List<SubEvent> findByEventFormat(EventFormat eventFormat);

    boolean existsBySubEventAndEventFormat(final String subEvent, final EventFormat eventFormat);
}

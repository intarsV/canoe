package com.initex.canoe.repository;

import com.initex.canoe.domain.EventFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventFormatRepository extends JpaRepository<EventFormat, Long> {
}

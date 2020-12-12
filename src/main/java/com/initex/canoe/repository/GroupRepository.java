package com.initex.canoe.repository;

import com.initex.canoe.domain.AgeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<AgeGroup, Long> {

    List<AgeGroup> findAllByDisabled(boolean disabled);

    boolean existsByAgeGroup(String group);

    @Query("UPDATE AgeGroup SET ageGroup = :ageGroup WHERE id = :id")
    int updateIfExists(@Param("ageGroup") String ageGroup, @Param("id") Long id);
}

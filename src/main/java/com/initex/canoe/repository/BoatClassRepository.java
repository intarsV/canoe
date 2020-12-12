package com.initex.canoe.repository;

import com.initex.canoe.domain.BoatClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BoatClassRepository extends JpaRepository<BoatClass, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE BoatClass SET boatClass = :boatClass WHERE id = :id")
    int updateIfExists(@Param("boatClass") String boatClass, @Param("id") Long id);
}

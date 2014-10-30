package com.selectyour.model.probe;

import com.selectyour.model.baseentity.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * repository for probes
 */
public interface ProbeRepository extends BaseRepository<Probe> {
    @Query("from Probe where clientId = :clientId")
    Probe findByClient(@Param("clientId") Long clientId);
}

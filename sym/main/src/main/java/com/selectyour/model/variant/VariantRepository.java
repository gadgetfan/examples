package com.selectyour.model.variant;

import com.selectyour.model.baseentity.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * repository for probes
 */
public interface VariantRepository extends BaseRepository<Variant> {
    @Query("from Variant where probeId = :probeId")
    Variant findVariantByProbe(@Param("probeId") Long probeId);
}

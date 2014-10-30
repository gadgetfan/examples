package com.selectyour.model.picture;

import com.selectyour.model.baseentity.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * abstract repository for named entity
 */
public interface PictureRepository extends BaseRepository<Picture> {
    @Query("select p from Picture p where p.styleId = :styleId")
    Page<Picture> findByStyle(
            @Param("styleId") Long styleId, Pageable pageable);

    /*Picture findOne(@Param("serieId") Long serieId,
                 @Param("modelId") Long modelId,
                 @Param("coveringId") Long coveringId,
                 @Param("glazingId") Long glazingId,
                 @Param("decorId") Long decorId);

    @Query("select d from Picture d where d.serieId = :serieId and d.coveringId = :coveringId")
    List<Picture> findBySerieAndCovering(
            @Param("serieId") Long serieId,
            @Param("coveringId") Long coveringId);*/
}

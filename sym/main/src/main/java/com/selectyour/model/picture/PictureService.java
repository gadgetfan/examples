package com.selectyour.model.picture;

import com.selectyour.gwtclient.rpc.dto.PictureDto;
import com.selectyour.model.baseentity.BaseService;
import org.springframework.data.domain.Page;

/**
 * interface for Door service
 */
public interface PictureService extends BaseService<Picture, PictureRepository> {
    Page<Picture> findByStyle(Long styleId, int rowsCnt);

    /**
     * doesn't set style. Iа style is needed see PictureInfo.setStyle dependencies
     *
     * @param picture
     * @return
     */
    PictureDto toPictureInfo(Picture picture, boolean isSmallPicture);

    /*Picture findOne(DoorUnq doorUnq);

    DoorUnq getDoorUnq(Long doorId);*/
}

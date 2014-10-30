package com.selectyour.model.picture;

import com.selectyour.gwtclient.rpc.dto.PictureDto;
import com.selectyour.gwtshared.PictureConstants;
import com.selectyour.model.baseentity.BaseServiceImpl;
import com.selectyour.utils.CurrencyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * base implementation of DoorService
 */
@Service
public class PictureServiceImpl extends BaseServiceImpl<Picture, PictureRepository> implements PictureService {
    private static final String RESIZED_IMAGE_DIR = "s";

    @Autowired
    @Override
    public void setRepo(PictureRepository repo) {
        super.setRepo(repo);
    }

    public Page<Picture> findByStyle(Long styleId, int rowsCnt) {
        return repo.findByStyle(styleId, new PageRequest(0, rowsCnt));
    }


    public PictureDto toPictureInfo(Picture picture, boolean isSmallPicture) {
        PictureDto result = new PictureDto();
        result.setId(picture.getId());
        result.setName(picture.getName());
        result.setPrice(String.format("%s руб.", CurrencyUtils.formatCurrency(picture.getPrice())));
        String dir = isSmallPicture ? RESIZED_IMAGE_DIR : null;
        result.setImageUrl(getImagePath(picture, dir));
        result.setKx((float) (picture.getWidth() / PictureConstants.MARKER_WIDTH));
        result.setKy((float) (picture.getHeight() / PictureConstants.MARKER_HEIGHT));
        result.setWidth(picture.getWidth());
        result.setHeight(picture.getHeight());

        return result;
    }

/*public Picture findOne(DoorUnq doorUnq) {
        return repo.findOne(doorUnq.getSerieId(), doorUnq.getModelId(), doorUnq.getCoveringId(),
                doorUnq.getGlazingId(), doorUnq.getDecorId());
    }

    public DoorUnq getDoorUnq(Long doorId) {
        Picture picture = findOne(doorId);
        DoorUnq doorUnq = new DoorUnq();
        doorUnq.setSerieId(picture.getSerieId());
        doorUnq.setModelId(picture.getModelId());
        doorUnq.setCoveringId(picture.getCoveringId());
        doorUnq.setGlazingId(picture.getGlazingId());
        doorUnq.setDecorId(picture.getDecorId());

        return doorUnq;
    }*/
}

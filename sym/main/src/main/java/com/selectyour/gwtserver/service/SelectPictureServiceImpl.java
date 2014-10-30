package com.selectyour.gwtserver.service;

import com.selectyour.gwtclient.rpc.dto.DataCell;
import com.selectyour.gwtclient.rpc.dto.DataRow;
import com.selectyour.gwtclient.rpc.dto.PictureDto;
import com.selectyour.gwtclient.rpc.exception.SelectDoorException;
import com.selectyour.gwtclient.rpc.service.SelectPictureService;
import com.selectyour.model.ModelConstants;
import com.selectyour.model.picture.Picture;
import com.selectyour.model.picture.PictureService;
import com.selectyour.model.style.Style;
import com.selectyour.model.style.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.ArrayList;
import java.util.List;

@Configurable
public class SelectPictureServiceImpl extends BaseSpringGwtServlet implements SelectPictureService {
    @Autowired
    PictureService pictureService;
    @Autowired
    StyleService styleService;

    public List<DataRow> getSelectStyleData(int maxDataCells) {
        List<DataRow> dataRowList = new ArrayList<DataRow>();
        for (Style style : styleService.findAll()) {
            final Long[] outSelectIds = new Long[]{style.getId()};
            DataRow dataRow = new DataRow(new DataCell(outSelectIds, new PictureDto(style.getDescription())));
            for (Picture picture : pictureService.findByStyle(style.getId(), maxDataCells)) {
                dataRow.addData(new DataCell(outSelectIds, pictureService.toPictureInfo(picture, true)));
            }
            if (dataRow.getData().size() > 0) {
                dataRowList.add(dataRow);
            }
        }

        return dataRowList;
    }

    public List<DataRow> getSelectPictureData(Long[] selectIds, int maxDataCells) {
        final Long styleId = selectIds[0];

        List<DataRow> dataRowList = new ArrayList<DataRow>();

        int d = 0;
        DataRow dataRow = null;
        for (Picture picture : pictureService.findByStyle(styleId, ModelConstants.FETCH_ALL_ROWS)) {
            final Long[] outSelectIds = new Long[]{picture.getId()};
            if ((d++ % maxDataCells) == 0) {
                if (dataRow != null) {
                    dataRowList.add(dataRow);
                }
                dataRow = new DataRow(null);
            }
            dataRow.addData(new DataCell(outSelectIds, pictureService.toPictureInfo(picture, true)));
        }
        if (dataRow != null) {
            dataRowList.add(dataRow);
        }

        return dataRowList;
    }

    public PictureDto getPictureInfo(Long pictureId) throws SelectDoorException {
        final Picture picture = pictureService.findOne(pictureId);

        if (picture != null) {
            //TODO: use lazy initialization for Door entity to get all names with picture.getStyle().getName()
            PictureDto result = pictureService.toPictureInfo(picture, false);
            result.setStyle(styleService.findOne(picture.getStyleId()).getName()); //TODO: arch set style in pictureService.toPictureInfo ?

            return result;
        } else {
            throw new SelectDoorException("Cannot find picture"); //TODO: make it work right ;) now returns incorrect message
        }
    }

}

package com.sprotect.io.web;

import com.sprotect.model.entity.photomethod.PhotoMethod;
import com.sprotect.model.entity.photomethod.PhotoMethodService;
import com.sprotect.model.entity.sign.Sign;
import com.sprotect.model.entity.sign.SignService;
import com.sprotect.model.image.AwtImageUtils;
import com.sprotect.model.image.ByteMatrix;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class DownloadedDataServiceImpl implements DownloadedDataService {
    @Autowired
    private PhotoMethodService photoMethodService;

    @Override
    public DownloadedData create(TransferredData transferredData) throws IOException {
        String signName = transferredData.getSignName();
        PhotoMethod photoMethod = photoMethodService.findOne(Long.valueOf(transferredData.getPhotoMethodId()));
        BufferedImage sourceSign = null;
        ByteMatrix thresholdSign = null;
        if (photoMethod != null) {
            sourceSign = AwtImageUtils.toBufferedImage(transferredData.getSourceSign()); //closes inputStream
            byte[] buffer = IOUtils.toByteArray(transferredData.getThresholdSign());
            transferredData.getThresholdSign().close();
            thresholdSign = new ByteMatrix(photoMethod.getWidth(), photoMethod.getHeight(), buffer);
        }

        if (!checkData(photoMethod, sourceSign, thresholdSign)) {
            return null;
        } else {
            return new DownloadedData(signName, sourceSign, thresholdSign, photoMethod);
        }
    }

    private boolean checkData(PhotoMethod photoMethod, BufferedImage sourceSign, ByteMatrix thresholdSign) {
        boolean result = true;
        if (photoMethod == null) {
            result = false;
        } else {
            if (photoMethod.getWidth() != sourceSign.getWidth() || photoMethod.getHeight() != sourceSign.getHeight()) {
                result = false;
            }
            if (photoMethod.getWidth() != thresholdSign.getWidth() || photoMethod.getHeight() != thresholdSign.getHeight()) {
                result = false;
            }
        }

        return result;
    }

}

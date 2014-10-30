package com.selectyour.pages.doors;

import com.selectyour.model.probe.ProbeService;
import com.selectyour.pages.BaseComponent;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;

import java.io.InputStream;

/**
 * Adds or change (deletes olad and adds new) photo of client's room.
 */
public class ChangePhoto extends BaseComponent {

    @Inject
    ProbeService probeService;

    @Property
    private UploadedFile file;

    /**
     * file upload error message
     */
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String message;

    //TODO: display error message on page
    Object onUploadException(FileUploadException ex) {
        message = "Ошибка загрузки файла: " + ex.getMessage();

        return this;
    }

    Object onSuccessFromUploadPhoto() {
        //save file to database
        byte[] photo = new byte[(int) file.getSize()];
        InputStream fileInputStream = null;
        try {
            try {
                fileInputStream = file.getStream();
                //convert file into array of bytes
                fileInputStream.read(photo);
            } finally {
                if (fileInputStream != null)
                    fileInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isRegisteredClient()) {
            probeService.savePhotoForRegisteredClient(getSession().getClientId(), photo);
        } else {
            String probeCookie = probeService.savePhotoForUnregisteredClient(getProbeIdCookie(), photo);
            setProbeIdCookie(probeCookie);
        }

        return Select.class;
    }
}

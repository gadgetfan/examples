package com.selectyour.pages;

import com.selectyour.model.probe.ProbeService;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Only displays canvas with user's photo
 */
public abstract class BaseComponent extends BasePage {
    @Inject
    ProbeService probeService;
    @InjectPage
    private ProbeHelper probeHelper;

    /**
     * Returns user's photo. Uses session info to load it
     *
     * @return
     */
    public Link getClientPhoto() {
        return probeHelper.getClientPhotoLink();
    }

    public String getWindowFrameData() {
        return probeHelper.getProbe().getWindowFrame();
    }

    protected String getProbeIdCookie() {
        return probeHelper.getProbeIdCookie();
    }

    protected void setProbeIdCookie(String probeIdCookie) {
        probeHelper.setProbeIdCookie(probeIdCookie);
    }

    protected void removeProbeIdCookie() {
        probeHelper.removeProbeIdCookie();
    }

    public boolean isClientPhotoUploaded() {
        return probeService.hasUploadedPhoto(getProbeIdCookie());
    }

    public void deleteClientPhoto() {
        if (isRegisteredClient()) {
            //TODO: do implementation
        } else {
            removeProbeIdCookie();
            probeService.deleteForUnregisteredClient(getProbeIdCookie());
        }

    }
}

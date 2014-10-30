package com.selectyour.pages.doors;

import com.selectyour.model.probe.Probe;
import com.selectyour.model.probe.ProbeService;
import com.selectyour.pages.BasePage;
import com.selectyour.pages.ProbeHelper;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Adds or change (deletes olad and adds new) photo of client's room.
 */
public class ChangeFrame extends BasePage {
    @Inject
    ProbeService probeService;

    @InjectPage
    private ProbeHelper probeHelper; //TODO: inherit page from BaseComponent and remove this injecting

    private String windowFrameData;

    private boolean shouldSaveWindowFrame;

    public void onActivate() {
        if (probeHelper.hasClientPhoto()) {
            Probe probe = probeHelper.getProbe();
            windowFrameData = probe.getWindowFrame();
        } else {
            windowFrameData = Probe.NO_WINDOW_FRAME_DATA;
        }
    }

    /**
     * returns, if user photo alreade exists. Should be checked before getClientPhoto call
     *
     * @return
     */
    private boolean isClientPhotoExists() {
        return probeHelper.hasClientPhoto();
    }

    /**
     * Returns user's photo. Uses session info to load it
     *
     * @return
     */
    public String getClientPhoto() {
        if (isClientPhotoExists()) {
            return probeHelper.getClientPhotoLink().toString();
        } else {
            return "";
        }
    }

    void onSelectedFromSaveWindowFrame() {
        shouldSaveWindowFrame = true;
    }

    void onSelectedFromClearWindowFrame() {
        shouldSaveWindowFrame = false;
    }

    Object onSuccessFromWindowFrame() {
        Probe probe = probeHelper.getProbe();
        if (shouldSaveWindowFrame) {
            probe.setWindowFrame(windowFrameData);
            probeService.save(probe);
            return Select.class;
        } else {
            probe.setWindowFrame(Probe.NO_WINDOW_FRAME_DATA);
            probeService.save(probe);
            return ChangeFrame.class;
        }
    }

    public String getWindowFrameData() {
        return windowFrameData;
    }

    public void setWindowFrameData(String windowFrameData) {
        this.windowFrameData = windowFrameData;
    }
}

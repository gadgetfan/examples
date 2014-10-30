package com.selectyour.pages;

import com.selectyour.model.probe.Probe;
import com.selectyour.model.probe.ProbeService;
import com.selectyour.tapestry.attachments.img.NeverExpiredJpeg;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

/**
 * Returns info about client's photo (Probe entity)
 */
public class ProbeHelper extends BasePage {
    //TODO: now PROBE_ID_COOKIE can be fabricated, save sessionID as cookie ?
    private static final String PROBE_ID_COOKIE = "probeId";
    @Inject
    ProbeService probeService;
    @Inject
    private Cookies cookies;
    @Inject
    private LinkSource linkSource;

    public Link getClientPhotoLink() {
        return linkSource.createPageRenderLink(ProbeHelper.class.getSimpleName(), false, getProbeIdCookie());
    }

    public boolean hasClientPhoto() {
        return (getProbe() != null);
    }

    public StreamResponse onActivate() {
        return new NeverExpiredJpeg(getProbe().getSourcePhoto());
    }

    String getProbeIdCookie() {
        return cookies.readCookieValue(PROBE_ID_COOKIE);
    }

    void setProbeIdCookie(String probeIdCookie) {
        cookies.writeCookieValue(PROBE_ID_COOKIE, probeIdCookie);
    }

    //TODO: should I cash Probe for speed or hibernate does it
    public Probe getProbe() {
        Probe probe;
        if (isRegisteredClient()) {
            probe = probeService.getForRegisteredClient(getSession().getClientId());
        } else {
            probe = probeService.getForUnregisteredClient(getProbeIdCookie());
        }

        return probe;
    }

    public void removeProbeIdCookie() {
        cookies.removeCookieValue(PROBE_ID_COOKIE);
    }
}
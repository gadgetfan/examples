package com.selectyour.model.probe;

import com.selectyour.model.baseentity.BaseServiceImpl;
import com.selectyour.model.client.ClientServiceImpl;
import com.selectyour.model.probe.recognition.RecognitionService;
import com.selectyour.utils.AwtImageUtils;
import com.selectyour.utils.Resizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * probe service implementation
 */
@Service
public final class ProbeServiceImpl extends BaseServiceImpl<Probe, ProbeRepository> implements ProbeService {
    private static final int MAX_PICTURE_AREA = 1600 * 1200;
    @Autowired
    private RecognitionService recognitionService;

    @Autowired
    @Override
    public void setRepo(ProbeRepository repo) {
        super.setRepo(repo);
    }

    public boolean hasUploadedPhoto(String probeCookie) {
        Long id = cookieToId(probeCookie);
        return (id != null && findOne(id) != null);
    }

    public void savePhotoForRegisteredClient(Long clientId, byte[] photo) {
        repo.delete(clientId); //needed to use NeverExpiredJpeg

        Probe probe = new Probe();
        probe.setClientId(clientId);
        setPhotoParams(photo, probe);
        repo.save(probe);
    }

    public String savePhotoForUnregisteredClient(String probeCookie, byte[] photo) {
        if (probeCookie != null) {
            repo.delete(cookieToId(probeCookie)); //needed to use NeverExpiredJpeg
        }
        Probe probe = new Probe();
        setPhotoParams(photo, probe);
        probe = repo.save(probe);

        return idToCookie(probe.getId());
    }

    public Probe getForRegisteredClient(Long clientId) {
        return repo.findByClient(clientId);
    }

    public Probe getForUnregisteredClient(String probeIdCookie) {
        Long id = cookieToId(probeIdCookie);
        if (id != null) {
            return repo.findOne(id);
        } else {
            return getDefault();
        }
    }

    public String createIfNeededAndGetForUnregisteredClient(String probeCookie) {
        Probe probe = getForUnregisteredClient(probeCookie);
        if (ClientServiceImpl.DEFAULT_CLIENT_ID.equals(probe.getClientId())) {
            probe = repo.save(getCopyForUnregisteredClient(probe));
        }

        return idToCookie(probe.getId());
    }

    private Probe getDefault() {
        return repo.findByClient(ClientServiceImpl.DEFAULT_CLIENT_ID);
    }

    private void setPhotoParams(byte[] photo, Probe probe) {
        BufferedImage bufferedImage = AwtImageUtils.toBufferedImage(photo);
        probe.setSourcePhoto(resizeImage(bufferedImage));
        probe.setWindowFrame(recognizeWindowFrame(bufferedImage));
    }

    /**
     * don't copies id, clientId fields
     *
     * @param srcProbe
     * @return
     */
    private Probe getCopyForUnregisteredClient(Probe srcProbe) {
        Probe dstProbe = new Probe();
        dstProbe.setSourcePhoto(srcProbe.getSourcePhoto());
        dstProbe.setWindowFrame(srcProbe.getWindowFrame());

        return dstProbe;
    }

    public void deleteForUnregisteredClient(String probeIdCookie) {
        Long id = cookieToId(probeIdCookie);
        if (id != null) {
            repo.delete(id);
        }
    }

    private String recognizeWindowFrame(BufferedImage image) {
        return recognitionService.recognizeWindowFrame(image);
    }

    private byte[] resizeImage(BufferedImage image) {
        float k = (float) Math.sqrt((float) image.getWidth() * image.getHeight() / MAX_PICTURE_AREA);
        BufferedImage resizedImage = Resizer.BICUBIC.resize(image,
                Math.round(image.getWidth() / k), Math.round(image.getHeight() / k));

        return AwtImageUtils.toByteArray(resizedImage);
    }

}

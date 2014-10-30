package com.selectyour.model.variant;

import com.selectyour.model.baseentity.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * probe service implementation
 */
@Service
public final class VariantServiceImpl extends BaseServiceImpl<Variant, VariantRepository> implements VariantService {
    @Autowired
    @Override
    public void setRepo(VariantRepository repo) {
        super.setRepo(repo);
    }

    public Variant changeVariantForUnregisteredClient(String probeCookie, String data) {
        Long probeId = cookieToId(probeCookie);
        Variant variant = repo.findVariantByProbe(probeId);
        if (variant == null) {
            variant = new Variant();
        }
        fillVariantFromData(variant, data);
        variant.setProbeId(probeId);

        return repo.save(variant);
    }

    public Variant addVariantForRegisteredClient(Long clientId, String data) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private Variant fillVariantFromData(Variant variant, String data) {
        if (data != null && !data.isEmpty()) {
            String[] datas = data.split(DATA_DELIMITER);
            int d = 0;

            variant.setProductImageSrc(datas[d++]);
            variant.setPrice(datas[d++]);
            StringBuilder description = new StringBuilder();
            for (; d < datas.length; ++d) {
                description.append(datas[d]);
                description.append(DATA_DELIMITER); //TODO: use substring to get needed string faster
            }
            variant.setDescription(description.toString());
        }

        return variant;
    }

    public Variant findForUnregisteredClient(String probeCookie) {
        return repo.findVariantByProbe(cookieToId(probeCookie));
    }
}

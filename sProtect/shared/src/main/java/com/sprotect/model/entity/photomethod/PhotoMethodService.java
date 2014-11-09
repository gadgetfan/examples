package com.sprotect.model.entity.photomethod;

import com.sprotect.model.entity.namedentity.NamedService;
import com.sprotect.model.entity.sign.*;
import com.sprotect.model.entity.sign.Sign;

import java.util.List;

/**
 * interface for PhotoMethod service
 */
public interface PhotoMethodService extends NamedService<PhotoMethod, PhotoMethodRepository> {
    List<PhotoMethod> findAll();
}

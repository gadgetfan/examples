package com.sprotect.model.entity.sign;

import com.sprotect.model.entity.namedentity.NamedService;

/**
 * interface for Sign service
 */
public interface SignService extends NamedService<Sign, SignRepository> {
    /**
     * searches for sign by its name. If sign is found, then returns it. Else creates and saves it.
     * @param name
     * @return
     */
    Sign findOrSaveByName(String name);
}

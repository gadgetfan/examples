package com.sprotect.model.entity.sign;

import com.sprotect.model.entity.namedentity.NamedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * base implementation of SignService
 */
@Service
public class SignServiceImpl extends NamedServiceImpl<Sign, SignRepository> implements SignService {
    @Autowired
    @Override
    public void setRepo(SignRepository repo) {
        super.setRepo(repo);
    }

    @Override
    public Sign findOrSaveByName(String name) {
        Sign sign = findOneByName(name);
        if (sign == null) {
            sign = save(new Sign(name));
        }

        return sign;
    }
}

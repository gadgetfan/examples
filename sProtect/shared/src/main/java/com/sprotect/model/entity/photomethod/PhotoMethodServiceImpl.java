package com.sprotect.model.entity.photomethod;

import com.sprotect.model.entity.namedentity.NamedServiceImpl;
import com.sprotect.model.entity.sign.Sign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * base implementation of PhotoMethodService
 */
@Service
public class PhotoMethodServiceImpl extends NamedServiceImpl<PhotoMethod, PhotoMethodRepository> implements PhotoMethodService {
    @Autowired
    @Override
    public void setRepo(PhotoMethodRepository repo) {
        super.setRepo(repo);
    }

    @Override
    public List<PhotoMethod> findAll() {
        return repo.findAll();
    }
}

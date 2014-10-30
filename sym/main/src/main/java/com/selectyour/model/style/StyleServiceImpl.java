package com.selectyour.model.style;

import com.selectyour.model.namedentity.NamedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * base implementation of StyleService
 */
@Service
public class StyleServiceImpl extends NamedServiceImpl<Style, StyleRepository> implements StyleService {
    @Autowired
    @Override
    public void setRepo(StyleRepository repo) {
        super.setRepo(repo);
    }

    public Iterable<Style> findAll() {
        return repo.findAll();
    }
}

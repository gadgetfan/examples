package com.selectyour.model.style;

import com.selectyour.model.namedentity.NamedService;

/**
 * interface for Style service
 */
public interface StyleService extends NamedService<Style, StyleRepository> {
    Iterable<Style> findAll();
}

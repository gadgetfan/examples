/** @author moiseyenko */
package com.sprotect.io.hibernate;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class WNamingStrategy extends ImprovedNamingStrategy {
    private static final long serialVersionUID = 2413979341288571583L;

    protected String makePlural(String s) {
        return s.endsWith("s") ? s + "es" : s + "s";
    }

    @Override
    public String classToTableName(String className) {
        return makePlural(super.classToTableName(className));
    }

    @Override
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName,
                                       String referencedColumnName) {
        String s = super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName, referencedColumnName);
        return s.endsWith("_id") ? s : s + "_id";
    }

}

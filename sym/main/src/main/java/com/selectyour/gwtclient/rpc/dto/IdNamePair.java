package com.selectyour.gwtclient.rpc.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class IdNamePair implements IsSerializable {
    private Long id;
    private String name;

    public IdNamePair() {
    }

    public IdNamePair(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Utility method for SimpleEntry and SimpleImmutableEntry.
     * Test for equality, checking for nulls.
     */
    private static boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object o) {
        if (!(o instanceof IdNamePair))
            return false;
        IdNamePair e = (IdNamePair) o;
        return eq(id, e.getId()) && eq(name, e.getName());
    }

    public int hashCode() {
        return (id == null ? 0 : id.hashCode()) ^
                (name == null ? 0 : name.hashCode());
    }

}

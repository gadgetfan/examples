package com.selectyour.gwtclient.rpc.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;

/**
 * possible glazing and decor for the picture
 */
public class GlazingDecorData implements IsSerializable {
    private List<IdNamePair> glazingList;
    private List<IdNamePair> decorList;
    private DoorUnq currentDoorUnq;

    public GlazingDecorData() {
    }

    public GlazingDecorData(List<IdNamePair> glazingList, List<IdNamePair> decorList, DoorUnq currentDoorUnq) {
        this.glazingList = glazingList;
        this.decorList = decorList;
        this.currentDoorUnq = currentDoorUnq;
    }

    public List<IdNamePair> getGlazingList() {
        return glazingList;
    }

    public List<IdNamePair> getDecorList() {
        return decorList;
    }

    public DoorUnq getCurrentDoorUnq() {
        return currentDoorUnq;
    }
}

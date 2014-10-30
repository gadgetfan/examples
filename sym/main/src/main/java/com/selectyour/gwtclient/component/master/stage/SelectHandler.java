package com.selectyour.gwtclient.component.master.stage;

/**
 * event, that occurs, after client chooses some variant on this stage
 */
public interface SelectHandler {
    void onSelect(Long[] selectIds);
}

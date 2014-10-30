package com.selectyour.gwtclient.component.master.stage;

/**
 * Stage in StageMaster
 */
public interface Stage {
    void show(Long[] selectIds);

    void hide();

    void setSelectHandler(SelectHandler selectHandler);
}

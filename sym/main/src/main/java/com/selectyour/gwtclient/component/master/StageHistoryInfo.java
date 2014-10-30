package com.selectyour.gwtclient.component.master;

/**
 * info about stage saved in browser history
 */
public class StageHistoryInfo {
    int stageIndex;
    Long[] selectIds;

    public StageHistoryInfo(int stageIndex, Long[] selectIds) {
        this.stageIndex = stageIndex;
        this.selectIds = selectIds;
    }

    public int getStageIndex() {
        return stageIndex;
    }

    public Long[] getSelectIds() {
        return selectIds;
    }
}

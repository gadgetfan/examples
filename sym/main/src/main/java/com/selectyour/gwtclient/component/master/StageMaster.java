package com.selectyour.gwtclient.component.master;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.selectyour.gwtclient.component.master.stage.SelectHandler;
import com.selectyour.gwtclient.component.master.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * shows stage in order, like they will be added
 * saves info for history, so user can go back and forth through stages
 */
public class StageMaster {
    static private final String TOKEN_DELIMITER = ";";
    /**
     * id is used in history (back button) support
     */
    private static final String COMPONENT_ID = "sm";
    private final List<Stage> stageList;
    private Stage currentStage;

    public StageMaster() {
        this.stageList = new ArrayList<Stage>();
        this.currentStage = null;
    }

    private void initHistorySupport() {
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            public void onValueChange(ValueChangeEvent<String> event) {
                processHistoryToken(event.getValue());
            }
        });
    }

    private void processHistoryToken(String token) {
        StageHistoryInfo stageHistoryInfo = decodeToken(token);
        if (stageHistoryInfo != null) {
            if (currentStage != null) {
                currentStage.hide();
            }
            currentStage = stageList.get(stageHistoryInfo.getStageIndex());
            currentStage.show(stageHistoryInfo.getSelectIds());
        }
    }

    public void addStage(final Stage stage) {
        stageList.add(stage);
        int size = stageList.size();
        if (size > 1) {
            final int stageIndex = size - 1;
            stageList.get(stageIndex - 1).setSelectHandler(new SelectHandler() {
                public void onSelect(Long[] selectIds) {
                    addHistoryInfoAndDisplay(stageIndex, selectIds);
                }
            });
        }
    }

    private void addHistoryInfoAndDisplay(int stageIndex, Long[] selectIds) {
        String token = encodeSelectIds(new StageHistoryInfo(stageIndex, selectIds));
        //if (History.getToken().equals(token)) {
        //    processHistoryToken(token);
        //} else {
        History.newItem(token);
        //}
    }

    private String encodeSelectIds(StageHistoryInfo stageHistoryInfo) {
        String token = COMPONENT_ID + TOKEN_DELIMITER;
        token += String.valueOf(stageHistoryInfo.getStageIndex()) + TOKEN_DELIMITER;
        if (stageHistoryInfo.getSelectIds() != null) {
            for (Long s : stageHistoryInfo.getSelectIds()) {
                token += s + TOKEN_DELIMITER;
            }
        }

        return token;
    }

    private StageHistoryInfo decodeToken(String token) {
        if (token != null && token.startsWith(COMPONENT_ID)) {
            String[] selects = token.split(TOKEN_DELIMITER);
            int stageIndex = Integer.valueOf(selects[1]);
            final int shift = 2;
            Long[] selectIds = null;
            if (selects.length > 2) {
                selectIds = new Long[selects.length - shift];
                for (int i = shift; i < selects.length; ++i) {
                    selectIds[i - shift] = Long.valueOf(selects[i]);
                }
            }

            return new StageHistoryInfo(stageIndex, selectIds);
        } else {
            return null;
        }
    }

    public void start(Long[] selectIds) {
        initHistorySupport();
        if (!processOldHistory()) {
            addHistoryInfoAndDisplay(0, selectIds);
        }
    }

    private boolean processOldHistory() {
        String token = History.getToken();
        if (!token.isEmpty()) {
            processHistoryToken(token);
            return true;
        } else {
            return false;
        }
    }
}

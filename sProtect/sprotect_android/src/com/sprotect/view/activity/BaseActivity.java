package com.sprotect.view.activity;// MaiseyenkaDP gdfan 23.09.12 12:56

import android.app.Activity;
import com.sprotect.SProtectApplication;

/**
 * ancestor of all activities
 */
public abstract class BaseActivity extends Activity {
    public SProtectApplication getApp() {
        return (SProtectApplication)getApplication();
    }
}

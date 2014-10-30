package com.selectyour.pages.doors;

import com.selectyour.pages.BaseComponent;

/**
 * dispatches requests to select page: show UploadPhotoAppeal page or not
 */
public class SelectDispatcher extends BaseComponent {
    private boolean showUploadPhotoAppeal = true;

    Object onActivate() {
        if (showUploadPhotoAppeal && !isClientPhotoUploaded() && !isRegisteredClient()) {
            return UploadPhotoAppeal.class;
        } else {
            return Select.class;
        }
    }
}

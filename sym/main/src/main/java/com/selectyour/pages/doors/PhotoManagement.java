package com.selectyour.pages.doors;

import com.selectyour.pages.BaseComponent;

/**
 * Management of client's photo
 */
public class PhotoManagement extends BaseComponent {
    Object onSuccessFromDeletePhoto() {
        deleteClientPhoto();

        return SelectDispatcher.class;
    }

    Object onSuccessFromChangePhoto() {
        return ChangePhoto.class;
    }

    /*Object onSuccessFromChangeFrame() {
        return ChangeFrame.class;
    }*/
}

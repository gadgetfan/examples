package com.sprotect.model.camera;

public enum CameraState {
    NO_CAMERA(0), CREATED(1), PREVIEW(2), IN_FOCUS(3), NEED_TAKE_PHOTO(4), PHOTO_MADE(5);

    private final int stage;

    CameraState(int stage) {
        this.stage = stage;
    }

    public boolean isAfterOrEqual(CameraState state) {
        return (this.stage >= state.stage);
    }
}

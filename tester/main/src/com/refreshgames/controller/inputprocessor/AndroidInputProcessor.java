package com.refreshgames.controller.inputprocessor;

import com.refreshgames.controller.detector.JumpDetector;
import com.refreshgames.other.debug.DebugUtils;

/**
 * Processes user input on android devices
 */
public class AndroidInputProcessor extends BaseInputProcessor {
    private static final float START_JUMP_VELOCITY = 1.2f;
    private JumpDetector jumpDetector;

    public AndroidInputProcessor() {
        jumpDetector = new JumpDetector();
        jumpDetector.init();
    }

    @Override
    public boolean accelerometerChanged(float x, float y, float z, float deltaTime) {
        jumpDetector.setNextAccelerometerData(x, y, z);
        float jumpVelocity = jumpDetector.getJumpVelocity(deltaTime);
        /*if (jumpVelocity > 0) {
            DebugUtils.addDebugMessage("jumpVelocity: " + jumpVelocity);
        }*/
        if (jumpVelocity > START_JUMP_VELOCITY) {
            controller.jump(jumpVelocity);
        }

        return true;
    }
}

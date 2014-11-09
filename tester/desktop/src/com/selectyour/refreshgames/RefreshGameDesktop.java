/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.selectyour.refreshgames;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.refreshgames.RefreshGame;
import com.refreshgames.other.utils.PrepareImageUtils;
import com.refreshgames.view.assets.Assets;

/**
 * Main game class for desktop
 */
public class RefreshGameDesktop {
    public static void main(String[] args) {
        //prepareImages();
        packImages();

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Refresh";
        cfg.useGL20 = false;
        cfg.width = 800;
        cfg.height = 480;
        new LwjglApplication(new RefreshGame(), cfg);
    }

    private static void prepareImages() {
        PrepareImageUtils.main(null);
    }

    /**
     * packs images for desktop/android version
     */
    private static void packImages() {
        TexturePacker2.Settings settings = new TexturePacker2.Settings();
        TexturePacker2.process(settings, Assets.IMAGES_DIRECTORY, Assets.PACKED_IMAGES_DIRECTORY,
                Assets.PACKED_IMAGE_NAME);
    }

}

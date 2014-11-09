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

package com.refreshgames.view.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for assets of game
 */
public class Assets {
    public static final float MAX_VOLUME = 1;
    public static final float SOUND_VOLUME = 0.75f;
    public static final float MUSIC_VOLUME = 0.5f;

    private static final List<Sound> soundList = new ArrayList<Sound>();

    public static final Sound COLLISION_SHORT = new Sound("sound/short_crash.wav");
    public static final Sound JUMP = new Sound("sound/jump.wav");
    public static final Sound VICTORY = new Sound("sound/victory.wav");
    public static final Sound SPARKLE = new Sound("sound/sparkle.wav");

    public static final Music FOREST = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));
    public static final Music MOTOR = Gdx.audio.newMusic(Gdx.files.internal("sound/motor.wav"));

    public static final String IMAGES_DIRECTORY = "images";
    public static final String PACKED_IMAGES_DIRECTORY = "images_packed";
    public static final String PACKED_IMAGE_NAME = "pack";

    public static TextureAtlas textureAtlas;
    static {reloadTextures();}
    public static void reloadTextures() {
        textureAtlas = new TextureAtlas(Gdx.files.internal(
                PACKED_IMAGES_DIRECTORY + "/" + PACKED_IMAGE_NAME + ".atlas"));
    }

    public static void playMusic(Music music) {
        music.setLooping(true);
        music.setVolume(MUSIC_VOLUME);
        //if (Settings.soundEnabled)
        music.play();
    }

    public static void playSounds() {
        //if (Settings.soundEnabled)
        for (Sound sound : soundList) {
            sound.play();
        }
    }

    private static void addSound(Sound sound) {
        soundList.add(sound);
    }

    public static class Sound {
        private com.badlogic.gdx.audio.Sound sound;
        private float volume = 0;

        private Sound(String fileName) {
            sound = Gdx.audio.newSound(Gdx.files.internal(fileName));
            addSound(this);
        }

        public void addVolume(float volume) {
            this.volume += volume;
        }

        private void play() {
            if (volume > 0) {
                if (volume > 1) {
                    volume = 1;
                }
                sound.play(volume);
            }
            volume = 0;
        }

        public void playStandard() {
            addVolume(SOUND_VOLUME);

            play();
        }

        public void playLoud() {
            addVolume(MAX_VOLUME);

            play();
        }
    }

    //TODO: need dispose all sounds, music, texture atlases
}

package com.jdp30.ArrowDrift.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

/**
 * Created by Jack on 09/07/2018.
 */
public class SoundUtil {

    private static SoundUtil instance;
    private float volume = 1.0f;
    private boolean enabled = true;
    protected final HashMap<String, Sound> sounds = new HashMap<String, Sound>();


    public Sound get(String key) {
        if (sounds.containsKey(key)) return sounds.get(key);
        if (Gdx.files.internal("sound/" + key + ".wav").exists()) {
            sounds.put(key, Gdx.audio.newSound(Gdx.files.internal("sound/" + key + ".wav")));
            return get(key);
        }
        return null;
    }

    public void play(String key) {
        if (!enabled || volume == 0.00f) return;
        Sound sound = get(key);
        if (sound == null) return;
        sound.play(volume);
    }


    public static SoundUtil getInstance() {
        if (instance == null)
            instance = new SoundUtil();
        return instance;
    }

    public static void dispose() {
        if (instance == null)
            return;
        else {
            for (Sound sound : SoundUtil.instance.sounds.values()) {
                sound.dispose();
            }
            instance = null;
        }
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

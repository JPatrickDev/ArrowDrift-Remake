package com.jdp30.ArrowDrift.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jdp30.ArrowDrift.game.GUI.LevelSelectDialog;
import com.jdp30.ArrowDrift.game.GUI.TextInputDialog;
import com.jdp30.ArrowDrift.game.Level.Level;
import storage.Node;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Jack Patrick on 02/06/2018.
 * <p>
 * Last Edit: 02/06/2018
 */
public class Util {

    public static final Random random = new Random();

    public static String[] reverse(String[] arr) {
        List<String> list = Arrays.asList(arr);
        Collections.reverse(list);
        return list.toArray(new String[0]);
    }

    public static String randHex() {
        int nextInt = random.nextInt(256 * 256 * 256);
        return String.format("#%06x", nextInt);
    }


    public static void input(String title, Stage parent, final TextDialogListener textCallback) {
        Skin skin = new Skin(Gdx.files.internal("ui/skin.json"));
        TextInputDialog text = new TextInputDialog("Select Level", skin) {
            @Override
            protected void result(Object object) {
                if (object.equals("OK")) {
                    textCallback.result(getText());
                } else {

                }
            }
        };
        text.show(parent);

    }

    interface TextDialogListener {

        public void result(String text);
    }
}

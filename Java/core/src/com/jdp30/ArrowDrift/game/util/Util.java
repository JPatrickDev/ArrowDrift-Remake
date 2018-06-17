package com.jdp30.ArrowDrift.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jdp30.ArrowDrift.game.GUI.LevelSelectDialog;
import com.jdp30.ArrowDrift.game.GUI.MenuDialog;
import com.jdp30.ArrowDrift.game.GUI.TextInputDialog;
import com.jdp30.ArrowDrift.game.Level.Level;
import storage.Node;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Jack Patrick on 02/06/2018.
 * <p/>
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
        TextInputDialog text = new TextInputDialog(title, skin) {
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

    //TODO Automatically handle the close option
    public static void menu(String title, String[] options, final TextDialogListener listener, final Stage parent) {
        Skin skin = new Skin(Gdx.files.internal("ui/skin.json"));
        MenuDialog text = new MenuDialog(title, skin, options) {
            @Override
            public void clicked(String option) {
                if(option.equalsIgnoreCase("Close") || option.equalsIgnoreCase("Back")){
                    remove();
                }else{

                }
            }
        };
        text.setListener(listener);
        text.show(parent);
    }

    public interface TextDialogListener {
        void result(String text);
    }
}

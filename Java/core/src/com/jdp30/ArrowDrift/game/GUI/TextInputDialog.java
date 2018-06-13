package com.jdp30.ArrowDrift.game.GUI;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Arrays;
import java.util.Comparator;

public class TextInputDialog extends Dialog {

    private Skin skin;
    private TextArea text;

    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }


    public TextInputDialog(String title, Skin skin) {
        super(title, skin);
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();

        this.button("Cancel", "Cancel");
        this.button("OK", "OK");

        this.setModal(true);
        this.skin = skin;

        text = new TextArea("", skin);
        this.getContentTable().add(text);
    }

    public TextInputDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
        this.setModal(true);
        this.skin = skin;
    }

    public String getText(){
        return text.getText();
    }


}

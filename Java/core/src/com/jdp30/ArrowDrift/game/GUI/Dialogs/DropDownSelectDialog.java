package com.jdp30.ArrowDrift.game.GUI.Dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdp30.ArrowDrift.game.ArrowDriftGame;
import storage.StorageSystem;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class DropDownSelectDialog extends Dialog {

    private Skin skin;
    private boolean firstRun = true;
    private SelectBox<String> box;

    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }

    public DropDownSelectDialog(String title, Skin skin,String[] items) {
        super(title, skin);
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();

        this.button("Cancel", "Cancel");
        this.button("OK", "OK");

        this.setModal(true);
        this.skin = skin;

        box = new SelectBox<String>(skin);
        box.setWidth(getWidth());
        box.setItems(items);
        box.setSelectedIndex(0);
        getContentTable().add(box);

    }

    public String getText() {
        return this.box.getSelected();
    }


    public void setCurrentItem(String currentItem) {
        if(box != null){
            box.setSelected(currentItem);
        }
    }
}

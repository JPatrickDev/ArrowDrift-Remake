package com.jdp30.ArrowDrift.game.GUI;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Arrays;
import java.util.Comparator;

public class MenuDialog extends Dialog {

    private Skin skin;
    private String[] options;

    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }

    public MenuDialog(String title, Skin skin, String[] options) {
        super(title, skin);
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();
        this.setModal(true);
        this.skin = skin;
        this.options = options;
        buildList();
    }

    public MenuDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
        this.setModal(true);
        this.skin = skin;
    }

    private void buildList() {
        ScrollPane pane = new ScrollPane(null, skin);
        Table table = new Table().top().left();
        table.defaults().left();
        ClickListener fileClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Label target = (Label) event.getTarget();

            }
        };
        TextButton button = null;
        for (String text : this.options) {
            table.row();
            button = new TextButton(text,skin);
            button.setName(text);
            button.addListener(fileClickListener);
            table.add(button).expandX().fillX();
        }
        pane.setWidget(table);
        this.getContentTable().reset();
        this.getContentTable().add(pane).maxHeight(300).expand().fill();
    }

}

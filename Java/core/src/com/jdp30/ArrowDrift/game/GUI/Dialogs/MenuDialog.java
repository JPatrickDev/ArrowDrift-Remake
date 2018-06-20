package com.jdp30.ArrowDrift.game.GUI.Dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdp30.ArrowDrift.game.util.Util;

public class MenuDialog extends Dialog {

    private Skin skin;
    private String[] options;
    private Util.DialogResultListener listener;
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


    private void buildList() {
        ScrollPane pane = new ScrollPane(null, skin);
        Table table = new Table().top().left();
        table.defaults().left();
        ClickListener fileClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(event.getTarget() instanceof Label){
                    Label l = (Label) event.getTarget();
                    MenuDialog.this.clicked(l.getText().toString());
                    if(listener != null)
                        listener.result(l.getText().toString());
                }

            }
        };
        TextButton button = null;
        for (String text : this.options) {
            table.row();
            button = new TextButton(text,skin);
            button.getLabelCell().padBottom(5f).padTop(5f).padLeft(5f).padRight(5f);
            button.setName(text);
            button.addListener(fileClickListener);
            table.add(button).expandX().fillX();
            table.row();
        }
        pane.setWidget(table);
        this.getContentTable().reset();
        this.getContentTable().add(pane).maxHeight(300).expand().fill();
    }

    public void clicked(String option){

    }
    public void setListener(Util.DialogResultListener listener){
        this.listener = listener;
    }
}

package com.jdp30.ArrowDrift.game.GUI;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import storage.Node;
import storage.StorageSystem;

import java.util.Arrays;
import java.util.Comparator;

public class LevelSelectDialog extends Dialog {

    private Skin skin;

    private StorageSystem system;
    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }

    public void setStorageSystem(StorageSystem system) {
        this.system = system;
        buildList(system);
    }


    public LevelSelectDialog(String title, Skin skin) {
        super(title, skin);
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();

        this.button("Cancel", "Cancel");
        this.button("OK", "OK");

        this.setModal(true);
        this.skin = skin;
    }

    public LevelSelectDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
        this.setModal(true);
        this.skin = skin;
    }

    public Node selected = null;

    public void setSelected(Node node){

        if(node == null){
            Label label = (Label) this.findActor("Add New");
            label.setColor(Color.RED);
        }
        if (this.selected != null) {
            Label label = (Label) this.findActor(this.selected.getName());
            label.setColor(Color.WHITE);
        }
        if(node == null)
            return;
        Label label = (Label) this.findActor("Add New");
        label.setColor(Color.WHITE);
        selected = node;
        label = (Label) this.findActor(selected.getName());
        label.setColor(Color.RED);
    }

    private void buildList(final StorageSystem mappack) {
        ScrollPane pane = new ScrollPane(null, skin);
        Table table = new Table().top().left();
        table.defaults().left();
        ClickListener fileClickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Label target = (Label) event.getTarget();
                for(Node n : mappack.getRoot().getChildren()){
                    if(n.getName().equals(target.getName())){
                        setSelected(n);
                        return;
                    }
                }
                setSelected(null);
            }
        };
        table.row();
        Label label = new Label("Add New", skin);
        label.setName("Add New");
        label.addListener(fileClickListener);
        table.add(label).expandX().fillX();
        for (Node sections : mappack.getRoot().getChildren()) {
            if(sections.getName().equals("metadata"))continue;
            table.row();
            label = new Label(sections.getName(), skin);
            label.setName(sections.getName());
            label.addListener(fileClickListener);
            table.add(label).expandX().fillX();
        }
        pane.setWidget(table);
        this.getContentTable().reset();
        this.getContentTable().add(pane).maxHeight(300).expand().fill();
        setSelected(null);
    }

}

package com.jdp30.ArrowDrift.game.GUI.Dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import storage.Node;

public class LevelSelectDialog extends Dialog {

    private Skin skin;

    protected Node baseNode;
    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }

    public void setNode(Node system) {
        this.baseNode = system;
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

    private void buildList(final Node mappack) {
        ScrollPane pane = new ScrollPane(null, skin);
        Table table = new Table().top().left();
        table.defaults().left();
        ClickListener fileClickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Label target = (Label) event.getTarget();
                for(Node n : mappack.getChildren()){
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
        for (Node sections : mappack.getChildren()) {
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

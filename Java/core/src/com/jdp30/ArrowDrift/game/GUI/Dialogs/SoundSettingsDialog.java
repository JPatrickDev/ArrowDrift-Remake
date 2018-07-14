package com.jdp30.ArrowDrift.game.GUI.Dialogs;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdp30.ArrowDrift.game.ArrowDriftGame;
import com.jdp30.ArrowDrift.game.util.SoundUtil;
import storage.Node;

/**
 * Created by Jack on 09/07/2018.
 */
public class SoundSettingsDialog extends Dialog {

    private Skin skin;

    protected Node baseNode;

    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }


    public SoundSettingsDialog(String title, Skin skin) {
        super(title, skin);
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();
        this.button("OK", "OK");
        this.setModal(true);
        this.skin = skin;


        final TextButton toggleSound = new TextButton("Disable Sound", skin);
        if(!SoundUtil.getInstance().isEnabled()){
            toggleSound.setText("Enable Sound");
        }
        getContentTable().add(toggleSound);
        getContentTable().row();

        final Table optionsTable = new Table();


        final Slider effectsSlider = new Slider(0, 100, 1, false, skin);
        effectsSlider.setValue(SoundUtil.getInstance().getVolume() * 100.0f);
        Label effectsLabel = new Label("Effects:", skin);
        final Label effectsValue = new Label(((int)effectsSlider.getValue()) + "%", skin);
        optionsTable.add(effectsLabel);
        optionsTable.add(effectsSlider);
        optionsTable.add(effectsValue);

        getContentTable().add(optionsTable).pad(10f);

        effectsSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (effectsSlider.getValue() != SoundUtil.getInstance().getVolume() * 100.0) {
                    SoundUtil.getInstance().setVolume(effectsSlider.getValue() / 100.0f);
                    String value = "";
                    if(SoundUtil.getInstance().getVolume() < 0.10){
                        value = ("  " +((int)Math.ceil(SoundUtil.getInstance().getVolume() * 100.0f)) + "%");
                    }else if(SoundUtil.getInstance().getVolume() == 1){
                        value = ((int)Math.ceil(SoundUtil.getInstance().getVolume() * 100.0f) + "%");
                    }else {
                      value = (" " +((int)Math.ceil(SoundUtil.getInstance().getVolume() * 100.0f)) + "%");
                    }
                    effectsValue.setText(value);
                }
                return false;
            }
        });

        toggleSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(toggleSound.getText().toString().equals("Disable Sound")){
                    toggleSound.setText("Enable Sound");
                    SoundUtil.getInstance().setEnabled(false);
                }else{
                    toggleSound.setText("Disable Sound");
                    SoundUtil.getInstance().setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        ArrowDriftGame.notifyStorageChanged();
    }
}
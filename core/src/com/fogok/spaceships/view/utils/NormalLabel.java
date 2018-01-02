package com.fogok.spaceships.view.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class NormalLabel extends Label {
    private String text;
    //TODO: delete this class!

    public NormalLabel(final CharSequence text, final LabelStyle style) {
        super(text, style);
        this.text = text.toString();
    }

    @Override
    public void act(final float delta) {
        this.setText(text);
        super.act(delta);
    }



    public void updateText(final String text) {
        this.text = text;
    }
}

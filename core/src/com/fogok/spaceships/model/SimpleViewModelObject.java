package com.fogok.spaceships.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface SimpleViewModelObject
{
    void draw(SpriteBatch batch);   //здесь мы должны засунуть во view нужные параметры из controller, затем отрисовать
}

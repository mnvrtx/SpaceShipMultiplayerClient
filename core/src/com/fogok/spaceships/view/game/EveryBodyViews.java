package com.fogok.spaceships.view.game;


import com.fogok.dataobjects.GameObjectsType;
import com.fogok.spaceships.view.View;
import com.fogok.spaceships.view.game.ships.SimpleShipView;
import com.fogok.spaceships.view.game.weapons.bullets.SimpleBlusterView;

public class EveryBodyViews {

    private View[] views;

    public EveryBodyViews() {

        views = new View[GameObjectsType.values().length];
        for (GameObjectsType gameObjectsType : GameObjectsType.values()) {
            int ord = gameObjectsType.ordinal();
            switch (gameObjectsType) {
                case SimpleBluster:
                    views[ord] = new SimpleBlusterView();
                    break;
                case SimpleShip:
                    views[ord] = new SimpleShipView();
                    break;
            }
        }

    }

    public <E extends GameObjectsType> View getView(E enumObject) {
        return views[enumObject.ordinal()];
    }
}

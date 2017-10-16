package com.fogok.spaceships.model.game;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.game.gameobjects.SpaceShipController;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.ViewModelObject;
import com.fogok.spaceships.view.game.SpaceShipView;

public class TempShipServer implements ViewModelObject {

    private SpaceShipView spaceShipView;
    private SpaceShipController controller;
    private NetworkData networkData;

    public TempShipServer(NetworkData networkData) {
        this.networkData = networkData;
        controller = new SpaceShipController(networkData);
        spaceShipView = new SpaceShipView();
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (networkData.getResponseJson() != null) {
            controller.handle(false);
            spaceShipView.draw(batch, controller);
        }
    }
}

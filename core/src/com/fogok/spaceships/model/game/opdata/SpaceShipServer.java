package com.fogok.spaceships.model.game.opdata;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fogok.spaceships.control.game.gameobjects.SpaceShipServerController;
import com.fogok.spaceships.model.NetworkData;
import com.fogok.spaceships.model.ViewModelObject;
import com.fogok.spaceships.view.game.SpaceShipView;

public class SpaceShipServer implements ViewModelObject {

    private SpaceShipView spaceShipView;
    private SpaceShipServerController controller;
    private NetworkData networkData;

    public SpaceShipServer(NetworkData networkData) {
        this.networkData = networkData;
        controller = new SpaceShipServerController(/*//TODO: COMPLETE THIS*/null, networkData);
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

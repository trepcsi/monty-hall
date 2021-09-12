package com.trepcsi.montyhall.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.trepcsi.montyhall.PlayScreen;
import com.trepcsi.montyhall.helper.GameState;

public class Door extends Sprite {

    private PlayScreen screen;
    private boolean isCar;

    public Door(float x, float y, boolean isCar, PlayScreen screen) {
        super(new Texture("door_close.png"));

        this.screen = screen;

        this.isCar = isCar;

        setPosition(x, y);
        setScale(.7f, .9f);
    }

    public void reveal() {
        if (isCar) {
            setRegion(new Texture("door_open_car.png"));
        } else {
            setRegion(new Texture("door_open_goat.png"));
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void clickInGame(float x, float y) {
        if (getBoundingRectangle().contains(x, y)) {
            if (isCar) {
                if (screen.gameState() != GameState.WIN && screen.gameState() != GameState.LOSE)
                    screen.changeState(GameState.WIN);
            } else {
                if (screen.gameState() != GameState.WIN && screen.gameState() != GameState.LOSE)
                    screen.changeState(GameState.LOSE);
            }
            reveal();
        }
    }

    public boolean isSelected(float x, float y) {
        return getBoundingRectangle().contains(x, y);
    }

    public boolean isCar() {
        return isCar;
    }
}

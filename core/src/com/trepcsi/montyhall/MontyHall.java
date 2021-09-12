package com.trepcsi.montyhall;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trepcsi.montyhall.screens.Menu;
import com.trepcsi.montyhall.screens.PlayScreen;

public class MontyHall extends Game {
    public static int V_WIDTH = 1200;
    public static int V_HEIGHT = 800;

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new Menu(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

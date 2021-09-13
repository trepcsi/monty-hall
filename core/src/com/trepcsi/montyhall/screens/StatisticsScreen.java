package com.trepcsi.montyhall.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.trepcsi.montyhall.MontyHall;
import com.trepcsi.montyhall.helper.TextManager;

public class StatisticsScreen implements Screen {

    private MontyHall game;
    private TextManager textManager;
    private PlayScreen playScreen;

    public StatisticsScreen(MontyHall game, PlayScreen playScreen, TextManager textManager) {
        this.game = game;
        this.playScreen = playScreen;
        this.textManager = textManager;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        textManager.printStatistics(game.batch);
        game.batch.end();

    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            game.setScreen(playScreen);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

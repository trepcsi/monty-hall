package com.trepcsi.montyhall.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trepcsi.montyhall.MontyHall;
import com.trepcsi.montyhall.helper.GameState;
import com.trepcsi.montyhall.helper.TextManager;
import com.trepcsi.montyhall.sprites.Door;

import java.util.concurrent.ThreadLocalRandom;

public class PlayScreen implements Screen {

    private final MontyHall game;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    private final Texture background;
    private final Texture replay;
    private final Texture statistics;

    private Array<Door> doors;

    private GameState gameState;
    private float clickTimer;

    private final TextManager textManager;

    int firstDoor = -1;
    int secondDoor = -1;

    public boolean toPublish = false;
    public boolean published = false;

    public PlayScreen(MontyHall game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        textManager = new TextManager();

        background = new Texture("background.jpg");
        replay = new Texture("Restart.png");
        statistics = new Texture("statistics.png");

        generateDoors();
        gameState = GameState.START;
        clickTimer = 0;
    }

    private void generateDoors() {
        int random = ThreadLocalRandom.current().nextInt(0, 2 + 1);

        doors = new Array<Door>();
        for (int i = 0; i < 3; i++) {
            if (i == random) {
                doors.add(new Door(i * 400 - 650, 100 - MontyHall.V_HEIGHT / 2.f, true, this));
            } else {
                doors.add(new Door(i * 400 - 650, 100 - MontyHall.V_HEIGHT / 2.f, false, this));
            }
        }
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, -(float) MontyHall.V_WIDTH / 2, -(float) MontyHall.V_HEIGHT / 2);
        for (Door door : doors) {
            door.draw(game.batch);
            textManager.draw(game.batch, gameState);
        }
        game.batch.draw(replay, -(float) MontyHall.V_WIDTH / 2 + 30, -(float) MontyHall.V_HEIGHT / 2 + 30, MontyHall.V_WIDTH / 20f, MontyHall.V_WIDTH / 20f);
        game.batch.draw(statistics, -(float) MontyHall.V_WIDTH / 2 + 30 + MontyHall.V_WIDTH / 20f + 30, -(float) MontyHall.V_HEIGHT / 2 + 30, MontyHall.V_WIDTH / 20f, MontyHall.V_WIDTH / 20f);
        game.batch.end();
    }

    //TODO fix first click can be anywhere
    private void handleInput(float dt) {
        clickTimer += dt;
        if (Gdx.input.isTouched() && clickTimer > 0.5f) {
            float worldX = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())).x;
            float worldY = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())).y;
            int i = 0;
            if (gameState == GameState.START) {
                for (Door d : doors) {
                    if (d.clickInGame(worldX, worldY, true)) {
                        gameState = GameState.AFTER_GUESS;
                        showGoat(worldX, worldY);
                        firstDoor = i;
                    }
                    i++;
                }

            } else {
                for (Door d : doors) {
                    if (d.clickInGame(worldX, worldY, false)) {
                        secondDoor = i;
                    }
                    i++;
                }
                if(toPublish && !published) {
                    publishResult();
                }
            }

            if (worldX > -(float) MontyHall.V_WIDTH / 2 + 30 && worldX < -(float) MontyHall.V_WIDTH / 2 + 30 + MontyHall.V_WIDTH / 20f
                    && worldY > -(float) MontyHall.V_HEIGHT / 2 + 30 && worldY < -(float) MontyHall.V_HEIGHT / 2 + 30 + MontyHall.V_WIDTH / 20f) {
                game.setScreen(new PlayScreen(game));
            }
            if (worldX > -(float) MontyHall.V_WIDTH / 2 + 30 + MontyHall.V_WIDTH / 20f + 30
                    && worldX < -(float) MontyHall.V_WIDTH / 2 + 30 + MontyHall.V_WIDTH / 20f + MontyHall.V_WIDTH / 20f + 30
                    && worldY > -(float) MontyHall.V_HEIGHT / 2 + 30
                    && worldY < -(float) MontyHall.V_HEIGHT / 2 + 30 + MontyHall.V_WIDTH / 20f) {
                game.setScreen(new StatisticsScreen(game, this, textManager));
            }
            clickTimer = 0;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.setScreen(new PlayScreen(game));
        }
    }

    private void showGoat(float worldX, float worldY) {
        int selectedIndex = 0;
        for (int i = 0; i < doors.size; i++) {
            if (doors.get(i).isSelected(worldX, worldY)) {
                selectedIndex = i;
            }
        }
        boolean goatRevealed = false;
        int randomIndex = -1;
        while (!goatRevealed) {
            randomIndex = ThreadLocalRandom.current().nextInt(0, 2 + 1);
            if (randomIndex != selectedIndex) {
                if (!doors.get(randomIndex).isCar()) {
                    doors.get(randomIndex).reveal();
                    goatRevealed = true;
                }
            }
        }
    }

    public void changeState(GameState state) {
        gameState = state;
    }

    public GameState gameState() {
        return gameState;
    }

    @Override
    public void show() {
        Gdx.gl.glViewport(0, 0, MontyHall.V_WIDTH, MontyHall.V_HEIGHT);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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

    private void publishResult() {
        System.out.println(firstDoor + " - " + secondDoor);
        published = true;
    }

    @Override
    public void dispose() {
        game.dispose();
        background.dispose();
    }
}

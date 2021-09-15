package com.trepcsi.montyhall.helper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trepcsi.montyhall.MontyHall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class TextManager {

    private final String begin = "2 Goats 1 Car, can you find it? Pick a door !";
    private final String afterGuess1 = "Look a Goat, would you like the stay or change?";
    private final String afterGuess2 = "Pick again !";
    private final String win = "Congratulation, You won!";
    private final String lose = "You lost, better luck next time!";

    private final BitmapFont font;

    public TextManager() {
        font = new BitmapFont();
        font.setColor(Color.ORANGE);
        font.getData().setScale(MontyHall.V_WIDTH / 600f);
    }

    public void draw(SpriteBatch batch, GameState state) {
        switch (state) {
            case START:
                font.draw(batch, begin, -MontyHall.V_WIDTH / 2.f + 100, MontyHall.V_HEIGHT / 4.f + 100);
                break;
            case AFTER_GUESS:
                font.draw(batch, afterGuess1, -MontyHall.V_WIDTH / 2.f + 100, MontyHall.V_HEIGHT / 4.f + 100);
                font.draw(batch, afterGuess2, -MontyHall.V_WIDTH / 2.f + 100, MontyHall.V_HEIGHT / 4.f + 50);
                break;

            case WIN:
                font.draw(batch, win, -MontyHall.V_WIDTH / 2.f + 100, MontyHall.V_HEIGHT / 4.f + 100);
                break;
            case LOSE:
                font.draw(batch, lose, -MontyHall.V_WIDTH / 2.f + 100, MontyHall.V_HEIGHT / 4.f + 100);
                break;
        }
    }

    public void printStatistics(SpriteBatch batch) {
        try {
            Scanner scanner = new Scanner(new File("statistics.txt"));
            String statistics = scanner.nextLine();

            float changeWinRate = (float) Character.getNumericValue(statistics.charAt(2)) / Character.getNumericValue(statistics.charAt(0)) * 100;
            float noChangeWinRate = (float) Character.getNumericValue(statistics.charAt(3)) / Character.getNumericValue(statistics.charAt(1)) * 100;
            font.getData().setScale(MontyHall.V_WIDTH / 450f);
            font.draw(batch, noChangeWinRate + "% when not swapping", -MontyHall.V_WIDTH / 2.f + 100, MontyHall.V_HEIGHT / 4.f + 100);
            font.draw(batch, changeWinRate + "% when swapping", -MontyHall.V_WIDTH / 2.f + 100, MontyHall.V_HEIGHT / 4.f + 20);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

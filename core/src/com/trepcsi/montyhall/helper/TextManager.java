package com.trepcsi.montyhall.helper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trepcsi.montyhall.MontyHall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
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
        Scanner reader = null;
        try {
            reader = new Scanner(new File("statistics.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line1 = reader.nextLine();
        System.out.println(line1);
        float gamesWhenChange = Integer.parseInt(line1);
        String line2 = reader.nextLine();
        float gamesWhenNoChange = Integer.parseInt(line2);
        String line3 = reader.nextLine();
        float winsWhenChange = Integer.parseInt(line3);
        String line4 = reader.nextLine();
        float winsWhenNoChange = Integer.parseInt(line4);
        reader.close();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        df.setMinimumFractionDigits(1);

        font.getData().setScale(MontyHall.V_WIDTH / 450f);
        font.draw(batch, df.format(winsWhenNoChange / gamesWhenNoChange * 100) + "% when not swapping", -MontyHall.V_WIDTH / 2.f + 100, MontyHall.V_HEIGHT / 4.f + 100);
        font.draw(batch, df.format(winsWhenChange / gamesWhenChange * 100) + "% when swapping", -MontyHall.V_WIDTH / 2.f + 100, MontyHall.V_HEIGHT / 4.f + 20);
    }
}

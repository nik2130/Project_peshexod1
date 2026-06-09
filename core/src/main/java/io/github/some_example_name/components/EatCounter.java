package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import io.github.some_example_name.GameSettings;

public class EatCounter {
    int x, y;
    BitmapFont font;

    public EatCounter(int x, int y) {
        this.x = x;
        this.y = y;

        font = new BitmapFont();
        font.getData().setScale(GameSettings.FONT_SCALE_EAT);
        font.setColor(Color.GREEN);
    }

    public void draw(Batch batch, int eat) {
        if (eat < GameSettings.EAT_RED_THRESHOLD) {
            font.setColor(Color.RED);
        } else if (eat < GameSettings.EAT_YELLOW_THRESHOLD) {
            font.setColor(Color.YELLOW);
        } else {
            font.setColor(Color.GREEN);
        }

        font.draw(batch, "Eat: " + eat + "%", x, y);
    }

    public void dispose() {
        font.dispose();
    }
}

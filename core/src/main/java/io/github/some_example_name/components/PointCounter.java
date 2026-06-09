package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import io.github.some_example_name.GameSettings;

public class PointCounter {
    int x, y;
    BitmapFont font;

    public PointCounter(int x, int y) {
        this.x = x;
        this.y = y;

        font = new BitmapFont();
        font.getData().setScale(GameSettings.FONT_SCALE_COUNTER);
        font.setColor(Color.WHITE);
    }

    public void draw(Batch batch, int countOfPoints) {
        font.draw(batch, "Count: " + countOfPoints, x, y);
    }

    public void draw(Batch batch, String prefix, int countOfPoints) {
        font.draw(batch, prefix + countOfPoints, x, y);
    }

    public void dispose() {
        font.dispose();
    }
}

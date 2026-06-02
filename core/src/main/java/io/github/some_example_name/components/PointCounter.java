package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PointCounter {
    int x, y;
    BitmapFont font;

    public PointCounter(int x, int y) {
        this.x = x;
        this.y = y;

        font = new BitmapFont();
        font.getData().setScale(3f);
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

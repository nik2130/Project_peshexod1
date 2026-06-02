package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class EatCounter {
    int x, y;
    BitmapFont font;

    public EatCounter(int x, int y) {
        this.x = x;
        this.y = y;

        font = new BitmapFont();
        font.getData().setScale(3f);
        font.setColor(Color.GREEN);
    }

    public void draw(Batch batch, int eat) {
        if (eat < 30) {
            font.setColor(Color.RED);
        } else if (eat < 50) {
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

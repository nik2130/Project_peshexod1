package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Counter {
    protected int x, y;
    protected BitmapFont font;

    public Counter(int x, int y, float fontScale, Color color) {
        this.x = x;
        this.y = y;
        font = new BitmapFont();
        font.getData().setScale(fontScale);
        font.setColor(color);
    }

    public void draw(Batch batch, String text) {
        font.draw(batch, text, x, y);
    }

    public void dispose() {
        font.dispose();
    }
}

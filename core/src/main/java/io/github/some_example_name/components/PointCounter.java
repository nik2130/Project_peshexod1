package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.github.some_example_name.Config.ScreenConfig;

public class PointCounter extends Counter {

    public PointCounter(int x, int y) {
        super(x, y, ScreenConfig.FONT_SCALE_COUNTER, Color.WHITE);
    }

    public void draw(Batch batch, String prefix, int countOfPoints) {
        font.draw(batch, prefix + countOfPoints, x, y);
    }
}

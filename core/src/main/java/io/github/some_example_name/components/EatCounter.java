package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.github.some_example_name.Config.GameplayConfig;
import io.github.some_example_name.Config.ScreenConfig;

public class EatCounter extends Counter {

    public EatCounter(int x, int y) {
        super(x, y, ScreenConfig.FONT_SCALE_EAT, Color.GREEN);
    }

    public void draw(Batch batch, int eat) {
        if (eat < GameplayConfig.EAT_RED_THRESHOLD) {
            font.setColor(Color.RED);
        } else if (eat < GameplayConfig.EAT_YELLOW_THRESHOLD) {
            font.setColor(Color.YELLOW);
        } else {
            font.setColor(Color.GREEN);
        }

        font.draw(batch, "Eat: " + eat + "%", x, y);
    }
}

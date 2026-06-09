package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.github.some_example_name.Config.GameplayConfig;
import io.github.some_example_name.Config.ScreenConfig;

public class MovingBackground {
    Texture texture;
    int texture1X;
    int texture2X;

    public MovingBackground(String pathToTexture) {
        texture = new Texture(pathToTexture);
        texture1X = 0;
        texture2X = ScreenConfig.SCR_WIDTH;
    }

    public void onDraw(Batch batch) {
        batch.draw(texture, texture1X, 0, ScreenConfig.SCR_WIDTH, ScreenConfig.SCR_HEIGHT);
        batch.draw(texture, texture2X, 0, ScreenConfig.SCR_WIDTH, ScreenConfig.SCR_HEIGHT);
    }

    public void move() {
        int speed = GameplayConfig.BACKGROUND_SCROLL_SPEED;

        texture1X -= speed;
        texture2X -= speed;

        if (texture1X <= -ScreenConfig.SCR_WIDTH) {
            texture1X = ScreenConfig.SCR_WIDTH;
        }
        if (texture2X <= -ScreenConfig.SCR_WIDTH) {
            texture2X = ScreenConfig.SCR_WIDTH;
        }
    }

    public void dispose() {
        texture.dispose();
    }
}

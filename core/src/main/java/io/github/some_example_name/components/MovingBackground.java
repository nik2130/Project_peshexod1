package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.github.some_example_name.GameSettings;

public class MovingBackground {
    Texture texture;
    int texture1X;
    int texture2X;

    public MovingBackground(String pathToTexture) {
        texture = new Texture(pathToTexture);
        texture1X = 0;
        texture2X = GameSettings.SCR_WIDTH;
    }

    public void onDraw(Batch batch) {
        batch.draw(texture, texture1X, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
        batch.draw(texture, texture2X, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
    }

    public void move() {
        int speed = GameSettings.BACKGROUND_SCROLL_SPEED;

        texture1X -= speed;
        texture2X -= speed;

        if (texture1X <= -GameSettings.SCR_WIDTH) {
            texture1X = GameSettings.SCR_WIDTH;
        }
        if (texture2X <= -GameSettings.SCR_WIDTH) {
            texture2X = GameSettings.SCR_WIDTH;
        }
    }

    public void dispose() {
        texture.dispose();
    }
}

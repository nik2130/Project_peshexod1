package io.github.some_example_name.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;

public class GameObject {
    public float x;
    public float y;
    public int width;
    public int height;
    public boolean isActive = true;
    protected Random random = new Random();
    protected Texture texture;

    public GameObject() {}

    public void draw(Batch batch) {
        if (isActive && texture != null) {
            batch.draw(texture, (int) x, (int) y, (float) width / 2, (float) height / 2, width, height, 1, 1, 180, 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
        }
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
        texture = null;
    }

    public boolean isCollision(GameObject other) {
        if (!isActive || !other.isActive) return false;
        return x < other.x + other.width &&
            x + width > other.x &&
            y < other.y + other.height &&
            y + height > other.y;
    }
}

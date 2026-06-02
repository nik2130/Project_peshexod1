package io.github.some_example_name.characters;

import static io.github.some_example_name.Main.SCR_HEIGHT;
import static io.github.some_example_name.Main.SCR_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.some_example_name.screens.ScreenGame;

public class Shaverm {
    Texture texture;
    public float x;
    public float y;
    public int width;
    public int height;
    Random random;
    public boolean isActive = true;

    public ScreenGame screenGame;

    private static List<Shaverm> activeShaverms = new ArrayList<>();

    private static final int SAFE_SPAWN_ZONE_X = SCR_WIDTH / 2 - 100;
    private static final int SAFE_SPAWN_ZONE_Y = 50;
    private static final int SAFE_SPAWN_ZONE_WIDTH = 300;
    private static final int SAFE_SPAWN_ZONE_HEIGHT = 200;

    public Shaverm(int shavermCount, int shavermIdx) {
        random = new Random();
        width = 100;
        height = 100;

        boolean positionFound = false;
        int attempts = 0;

        while (!positionFound && attempts < 30) {
            x = random.nextInt(SCR_WIDTH - width);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > SAFE_SPAWN_ZONE_X &&
                    x < SAFE_SPAWN_ZONE_X + SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > SAFE_SPAWN_ZONE_Y &&
                    y < SAFE_SPAWN_ZONE_Y + SAFE_SPAWN_ZONE_HEIGHT;

            if (!inSafeZone && !hasCollisionWithOtherShaverms()) {
                positionFound = true;
            }
            attempts++;
        }

        if (!positionFound) {
            x = random.nextInt(SCR_WIDTH - width);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > SAFE_SPAWN_ZONE_X &&
                    x < SAFE_SPAWN_ZONE_X + SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > SAFE_SPAWN_ZONE_Y &&
                    y < SAFE_SPAWN_ZONE_Y + SAFE_SPAWN_ZONE_HEIGHT;

            if (inSafeZone) {
                isActive = false;
                return;
            }
        }

        activeShaverms.add(this);
        texture = new Texture("images/Shaverma.png");
    }

    private boolean hasCollisionWithOtherShaverms() {
        for (Shaverm otherShaverm : activeShaverms) {
            if (otherShaverm != this && otherShaverm.isActive) {
                boolean xOverlap = Math.abs(this.x - otherShaverm.x) < this.width * 1.2f;
                boolean yOverlap = Math.abs(this.y - otherShaverm.y) < this.height * 1.2f;

                if (xOverlap && yOverlap) {
                    return true;
                }
            }
        }
        return false;
    }

    public void draw(Batch batch) {
        if (isActive && texture != null) {
            batch.draw(texture, (int) x, (int) y, width / 2, height / 2, width, height, 1, 1, 180, 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
        }
    }

    public void dispose() {
        if (texture != null && texture != screenGame.shavermTex) {
            texture.dispose();
        }
        texture = null;
        activeShaverms.remove(this);
    }

    public void moveWithBackground(int speed) {
        if (!isActive) return;

        x -= speed;

        if (x < -100) {
            deactivate();
        }
    }

    private void deactivate() {
        isActive = false;
        activeShaverms.remove(this);
        if (texture != null && texture != screenGame.shavermTex) {
            texture.dispose();
        }
        texture = null;
    }

    public void reset(float maxX) {
        if (activeShaverms.contains(this)) {
            activeShaverms.remove(this);
        }

        Random random = new Random();
        boolean positionFound = false;
        int attempts = 0;

        while (!positionFound && attempts < 30) {
            x = maxX + 400 + random.nextInt(200);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > SAFE_SPAWN_ZONE_X &&
                    x < SAFE_SPAWN_ZONE_X + SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > SAFE_SPAWN_ZONE_Y &&
                    y < SAFE_SPAWN_ZONE_Y + SAFE_SPAWN_ZONE_HEIGHT;

            if (!inSafeZone && !hasCollisionWithOtherShaverms()) {
                positionFound = true;
            }
            attempts++;
        }

        if (!positionFound) {
            x = maxX + 400 + random.nextInt(200);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > SAFE_SPAWN_ZONE_X &&
                    x < SAFE_SPAWN_ZONE_X + SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > SAFE_SPAWN_ZONE_Y &&
                    y < SAFE_SPAWN_ZONE_Y + SAFE_SPAWN_ZONE_HEIGHT;

            if (inSafeZone) {
                isActive = false;
                return;
            }
        }

        isActive = true;
        activeShaverms.add(this);

        if (texture != null && texture != screenGame.shavermTex) {
            texture.dispose();
        }
        texture = screenGame.shavermTex;
    }

    public boolean isCollision(Person person) {
        if (!isActive) return false;

        return x < person.x + person.width &&
            x + width > person.x &&
            y < person.y + person.height &&
            y + height > person.y;
    }

}

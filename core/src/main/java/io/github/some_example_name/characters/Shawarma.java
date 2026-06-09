package io.github.some_example_name.characters;

import static io.github.some_example_name.GameSettings.SCR_HEIGHT;
import static io.github.some_example_name.GameSettings.SCR_WIDTH;

import java.util.ArrayList;
import java.util.List;

import io.github.some_example_name.GameSettings;
import io.github.some_example_name.Resurces;

public class Shawarma extends GameObject {
    private static List<Shawarma> activeShawarmas = new ArrayList<>();

    public Shawarma(int shavermCount, int shavermIdx) {
        width = GameSettings.SHAWARMA_WIDTH;
        height = GameSettings.SHAWARMA_HEIGHT;

        boolean positionFound = false;
        int attempts = 0;

        while (!positionFound && attempts < GameSettings.SPAWN_ATTEMPTS) {
            x = random.nextInt(SCR_WIDTH - width);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > GameSettings.SAFE_SPAWN_ZONE_X &&
                    x < GameSettings.SAFE_SPAWN_ZONE_X + GameSettings.SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > GameSettings.SAFE_SPAWN_ZONE_Y &&
                    y < GameSettings.SAFE_SPAWN_ZONE_Y + GameSettings.SAFE_SPAWN_ZONE_HEIGHT;

            if (!inSafeZone && !hasCollisionWithOtherShaverms()) {
                positionFound = true;
            }
            attempts++;
        }

        if (!positionFound) {
            x = random.nextInt(SCR_WIDTH - width);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > GameSettings.SAFE_SPAWN_ZONE_X &&
                    x < GameSettings.SAFE_SPAWN_ZONE_X + GameSettings.SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > GameSettings.SAFE_SPAWN_ZONE_Y &&
                    y < GameSettings.SAFE_SPAWN_ZONE_Y + GameSettings.SAFE_SPAWN_ZONE_HEIGHT;

            if (inSafeZone) {
                isActive = false;
                return;
            }
        }

        activeShawarmas.add(this);
        texture = Resurces.loadTexture(Resurces.PATH_SHAVERM);
    }

    private boolean hasCollisionWithOtherShaverms() {
        for (Shawarma otherShawarma : activeShawarmas) {
            if (otherShawarma != this && otherShawarma.isActive) {
                boolean xOverlap = Math.abs(this.x - otherShawarma.x) < this.width * GameSettings.COLLISION_STRICT_FACTOR;
                boolean yOverlap = Math.abs(this.y - otherShawarma.y) < this.height * GameSettings.COLLISION_STRICT_FACTOR;

                if (xOverlap && yOverlap) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void dispose() {
        if (texture != null && texture != Resurces.shavermTex) {
            texture.dispose();
        }
        texture = null;
        activeShawarmas.remove(this);
    }

    public void moveWithBackground(int speed) {
        if (!isActive) return;

        x -= speed;

        if (x < GameSettings.HATCH_DEACTIVATE_X) {
            deactivate();
        }
    }

    private void deactivate() {
        isActive = false;
        activeShawarmas.remove(this);
        if (texture != null && texture != Resurces.shavermTex) {
            texture.dispose();
        }
        texture = null;
    }

    public void reset(float maxX) {
        if (activeShawarmas.contains(this)) {
            activeShawarmas.remove(this);
        }

        boolean positionFound = false;
        int attempts = 0;

        while (!positionFound && attempts < GameSettings.SPAWN_ATTEMPTS) {
            x = maxX + GameSettings.SHAWARMA_RESET_X_OFFSET + random.nextInt(GameSettings.SHAWARMA_RESET_X_RANDOM);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > GameSettings.SAFE_SPAWN_ZONE_X &&
                    x < GameSettings.SAFE_SPAWN_ZONE_X + GameSettings.SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > GameSettings.SAFE_SPAWN_ZONE_Y &&
                    y < GameSettings.SAFE_SPAWN_ZONE_Y + GameSettings.SAFE_SPAWN_ZONE_HEIGHT;

            if (!inSafeZone && !hasCollisionWithOtherShaverms()) {
                positionFound = true;
            }
            attempts++;
        }

        if (!positionFound) {
            x = maxX + GameSettings.SHAWARMA_RESET_X_OFFSET + random.nextInt(GameSettings.SHAWARMA_RESET_X_RANDOM);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > GameSettings.SAFE_SPAWN_ZONE_X &&
                    x < GameSettings.SAFE_SPAWN_ZONE_X + GameSettings.SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > GameSettings.SAFE_SPAWN_ZONE_Y &&
                    y < GameSettings.SAFE_SPAWN_ZONE_Y + GameSettings.SAFE_SPAWN_ZONE_HEIGHT;

            if (inSafeZone) {
                isActive = false;
                return;
            }
        }

        isActive = true;
        activeShawarmas.add(this);

        if (texture != null && texture != Resurces.shavermTex) {
            texture.dispose();
        }
        texture = Resurces.shavermTex;
    }
}

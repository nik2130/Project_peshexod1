package io.github.some_example_name.characters;

import static io.github.some_example_name.Config.ScreenConfig.SCR_HEIGHT;
import static io.github.some_example_name.Config.ScreenConfig.SCR_WIDTH;

import java.util.ArrayList;
import java.util.List;

import io.github.some_example_name.Config.EntityConfig;
import io.github.some_example_name.Config.GameplayConfig;
import io.github.some_example_name.Config.SpawnConfig;

import io.github.some_example_name.Resurces;

public class Shawarma extends GameObject {
    private static final List<Shawarma> activeShawarmas = new ArrayList<>();

    public Shawarma() {
        width = EntityConfig.SHAWARMA_WIDTH;
        height = EntityConfig.SHAWARMA_HEIGHT;

        boolean positionFound = false;
        int attempts = 0;

        while (!positionFound && attempts < SpawnConfig.SPAWN_ATTEMPTS) {
            x = random.nextInt(SCR_WIDTH - width);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > SpawnConfig.SAFE_SPAWN_ZONE_X &&
                    x < SpawnConfig.SAFE_SPAWN_ZONE_X + SpawnConfig.SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > SpawnConfig.SAFE_SPAWN_ZONE_Y &&
                    y < SpawnConfig.SAFE_SPAWN_ZONE_Y + SpawnConfig.SAFE_SPAWN_ZONE_HEIGHT;

            if (!inSafeZone && hasCollisionWithOtherShaverms()) {
                positionFound = true;
            }
            attempts++;
        }

        if (!positionFound) {
            x = random.nextInt(SCR_WIDTH - width);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > SpawnConfig.SAFE_SPAWN_ZONE_X &&
                    x < SpawnConfig.SAFE_SPAWN_ZONE_X + SpawnConfig.SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > SpawnConfig.SAFE_SPAWN_ZONE_Y &&
                    y < SpawnConfig.SAFE_SPAWN_ZONE_Y + SpawnConfig.SAFE_SPAWN_ZONE_HEIGHT;

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
                boolean xOverlap = Math.abs(this.x - otherShawarma.x) < this.width * GameplayConfig.COLLISION_STRICT_FACTOR;
                boolean yOverlap = Math.abs(this.y - otherShawarma.y) < this.height * GameplayConfig.COLLISION_STRICT_FACTOR;

                if (xOverlap && yOverlap) {
                    return false;
                }
            }
        }
        return true;
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

        if (x < EntityConfig.HATCH_DEACTIVATE_X) {
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
        activeShawarmas.remove(this);

        boolean positionFound = false;
        int attempts = 0;

        while (!positionFound && attempts < SpawnConfig.SPAWN_ATTEMPTS) {
            x = maxX + SpawnConfig.SHAWARMA_RESET_X_OFFSET + random.nextInt(SpawnConfig.SHAWARMA_RESET_X_RANDOM);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > SpawnConfig.SAFE_SPAWN_ZONE_X &&
                    x < SpawnConfig.SAFE_SPAWN_ZONE_X + SpawnConfig.SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > SpawnConfig.SAFE_SPAWN_ZONE_Y &&
                    y < SpawnConfig.SAFE_SPAWN_ZONE_Y + SpawnConfig.SAFE_SPAWN_ZONE_HEIGHT;

            if (!inSafeZone && hasCollisionWithOtherShaverms()) {
                positionFound = true;
            }
            attempts++;
        }

        if (!positionFound) {
            x = maxX + SpawnConfig.SHAWARMA_RESET_X_OFFSET + random.nextInt(SpawnConfig.SHAWARMA_RESET_X_RANDOM);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > SpawnConfig.SAFE_SPAWN_ZONE_X &&
                    x < SpawnConfig.SAFE_SPAWN_ZONE_X + SpawnConfig.SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > SpawnConfig.SAFE_SPAWN_ZONE_Y &&
                    y < SpawnConfig.SAFE_SPAWN_ZONE_Y + SpawnConfig.SAFE_SPAWN_ZONE_HEIGHT;

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

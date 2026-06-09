package io.github.some_example_name.characters;

import static io.github.some_example_name.Config.ScreenConfig.SCR_HEIGHT;
import static io.github.some_example_name.Config.ScreenConfig.SCR_WIDTH;

import java.util.ArrayList;
import java.util.List;

import io.github.some_example_name.Config.EntityConfig;
import io.github.some_example_name.Config.GameplayConfig;
import io.github.some_example_name.Config.SpawnConfig;

import io.github.some_example_name.Resurces;

public class Hatch extends GameObject {
    private static final List<Hatch> activeLukes = new ArrayList<>();

    public Hatch(int lukesCount, int lukesIdx) {
        width = EntityConfig.HATCH_WIDTH;
        height = EntityConfig.HATCH_HEIGHT;

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

            if (!inSafeZone && hasCollisionWithOtherLukes()) {
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

        activeLukes.add(this);
        texture = Resurces.loadTexture(Resurces.PATH_LUKE);
    }

    private boolean hasCollisionWithOtherLukes() {
        for (Hatch otherHatch : activeLukes) {
            if (otherHatch != this && otherHatch.isActive) {
                boolean xOverlap = Math.abs(this.x - otherHatch.x) < this.width * GameplayConfig.COLLISION_STRICT_FACTOR;
                boolean yOverlap = Math.abs(this.y - otherHatch.y) < this.height * GameplayConfig.COLLISION_STRICT_FACTOR;

                if (xOverlap && yOverlap) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void dispose() {
        if (texture != null && texture != Resurces.lukest) {
            texture.dispose();
        }
        texture = null;
        activeLukes.remove(this);
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
        activeLukes.remove(this);
        if (texture != null && texture != Resurces.lukest) {
            texture.dispose();
        }
        texture = null;
    }

    public void reset(float maxX) {
        activeLukes.remove(this);

        boolean positionFound = false;
        int attempts = 0;

        while (!positionFound && attempts < SpawnConfig.SPAWN_ATTEMPTS) {
            x = maxX + SpawnConfig.HATCH_RESET_X_OFFSET + random.nextInt(SpawnConfig.HATCH_RESET_X_RANDOM);
            y = random.nextInt(SCR_HEIGHT - height - 100) + 50;

            boolean inSafeZone =
                x + width > SpawnConfig.SAFE_SPAWN_ZONE_X &&
                    x < SpawnConfig.SAFE_SPAWN_ZONE_X + SpawnConfig.SAFE_SPAWN_ZONE_WIDTH &&
                    y + height > SpawnConfig.SAFE_SPAWN_ZONE_Y &&
                    y < SpawnConfig.SAFE_SPAWN_ZONE_Y + SpawnConfig.SAFE_SPAWN_ZONE_HEIGHT;

            if (!inSafeZone && hasCollisionWithOtherLukes()) {
                positionFound = true;
            }
            attempts++;
        }

        if (!positionFound) {
            x = maxX + SpawnConfig.HATCH_RESET_X_OFFSET + random.nextInt(SpawnConfig.HATCH_RESET_X_RANDOM);
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
        activeLukes.add(this);

        if (texture != null) {
            texture.dispose();
        }
        texture = Resurces.lukest;
    }
}

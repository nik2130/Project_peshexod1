package io.github.some_example_name.characters;

import static io.github.some_example_name.Config.ScreenConfig.SCR_HEIGHT;
import static io.github.some_example_name.Config.ScreenConfig.SCR_WIDTH;

import java.util.ArrayList;
import java.util.List;

import io.github.some_example_name.Config.EntityConfig;
import io.github.some_example_name.Config.GameplayConfig;
import io.github.some_example_name.Config.ScreenConfig;
import io.github.some_example_name.Config.SpawnConfig;
import io.github.some_example_name.Resurces;

public class Car extends GameObject {
    int downSpeed;
    int lane;

    private static final List<Car> activeCars = new ArrayList<>();

    public Car(int carCount, int carIdx) {
        width = EntityConfig.CAR_WIDTH;
        height = EntityConfig.CAR_HEIGHT;
        downSpeed = GameplayConfig.CAR_DOWN_SPEED_MIN + random.nextInt(GameplayConfig.CAR_DOWN_SPEED_RANGE);

        isActive = false;
        x = -SCR_WIDTH - width;
        y = -SCR_HEIGHT - height;
    }

    @Override
    public void dispose() {
        if (texture != null && texture != Resurces.carTex) {
            texture.dispose();
        }
        texture = null;
        activeCars.remove(this);
    }

    public void moveWithBackground(int speed) {
        if (!isActive) return;

        x -= speed;

        if (x < -width) {
            resetConstantly(SCR_HEIGHT + 200);
        }
    }

    public void moveDown() {
        if (!isActive) return;

        y -= downSpeed;
    }

    public void resetConstantly(float newY) {
        if (activeCars.contains(this)) {
            activeCars.remove(this);
        }

        boolean positionFound = false;
        int attempts = 0;

        while (!positionFound && attempts < SpawnConfig.SPAWN_ATTEMPTS) {
            lane = random.nextInt(GameplayConfig.LANE_COUNT);
            x = lane * ScreenConfig.LANE_WIDTH + (float) (ScreenConfig.LANE_WIDTH - width) / GameplayConfig.CAR_DOWN_SPEED_MIN;
            y = newY;

            if (!hasCollisionWithOtherCarsStrict()) {
                positionFound = true;
            }
            attempts++;
        }

        if (!positionFound) {
            lane = random.nextInt(GameplayConfig.LANE_COUNT);
            x = lane * ScreenConfig.LANE_WIDTH + (float) (ScreenConfig.LANE_WIDTH - width) / GameplayConfig.CAR_DOWN_SPEED_MIN;
            y = newY;
        }

        downSpeed = GameplayConfig.CAR_DOWN_SPEED_MIN + random.nextInt(GameplayConfig.CAR_DOWN_SPEED_RANGE);
        isActive = true;
        activeCars.add(this);

        if (texture != null && texture != Resurces.carTex) {
            texture.dispose();
        }
        texture = Resurces.carTex;
    }

    private boolean hasCollisionWithOtherCarsStrict() {
        for (Car otherCar : activeCars) {
            if (otherCar != this && otherCar.isActive) {
                boolean xOverlap = Math.abs(this.x - otherCar.x) < this.width * GameplayConfig.COLLISION_STRICT_FACTOR;
                boolean yOverlap = Math.abs(this.y - otherCar.y) < this.height * GameplayConfig.COLLISION_STRICT_FACTOR;

                if (xOverlap && yOverlap) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasCollisionWithOtherCars() {
        for (Car otherCar : activeCars) {
            if (otherCar != this && otherCar.isActive) {
                boolean xOverlap = Math.abs(this.x - otherCar.x) < this.width * GameplayConfig.COLLISION_LOOSE_FACTOR;
                boolean yOverlap = Math.abs(this.y - otherCar.y) < this.height * GameplayConfig.COLLISION_LOOSE_FACTOR;

                if (xOverlap && yOverlap) {
                    return true;
                }
            }
        }
        return false;
    }
}

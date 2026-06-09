package io.github.some_example_name.characters;

import static io.github.some_example_name.Main.SCR_HEIGHT;
import static io.github.some_example_name.Main.SCR_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.some_example_name.Resurces;

public class Car {
    Texture texture;
    public float x;
    public float y;
    public int width;
    public int height;
    int downSpeed;
    int lane;
    Random random;
    public boolean isActive = true;
    final int LANE_WIDTH = SCR_WIDTH / 2;
    static final int LANE_COUNT = 5;
    private static long[] laneCooldowns = new long[LANE_COUNT];

    private static List<Car> activeCars = new ArrayList<>();

    static {
        for (int i = 0; i < LANE_COUNT; i++) {
            laneCooldowns[i] = 100;
        }
    }

    public Car(int carCount, int carIdx) {
        random = new Random();
        width = 120;
        height = 200;
        downSpeed = 3 + random.nextInt(4);

        isActive = false;
        x = -1000;
        y = -1000;
    }


    public void draw(Batch batch) {
        if (isActive && texture != null) {
            batch.draw(texture, (int) x, (int) y, width / 2, height / 2, width, height, 1, 1, 180, 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
        }
    }

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

        while (!positionFound && attempts < 30) {
            lane = random.nextInt(LANE_COUNT);
            x = lane * LANE_WIDTH + (LANE_WIDTH - width) / 3;
            y = newY;

            if (!hasCollisionWithOtherCarsStrict()) {
                positionFound = true;
            }
            attempts++;
        }

        if (!positionFound) {
            lane = random.nextInt(LANE_COUNT);
            x = lane * LANE_WIDTH + (LANE_WIDTH - width) / 3;
            y = newY;
        }

        laneCooldowns[lane] = System.currentTimeMillis();
        downSpeed = 3 + random.nextInt(4);
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
                boolean xOverlap = Math.abs(this.x - otherCar.x) < this.width * 1.2f;
                boolean yOverlap = Math.abs(this.y - otherCar.y) < this.height * 1.2f;

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
                boolean xOverlap = Math.abs(this.x - otherCar.x) < this.width * 0.8f;
                boolean yOverlap = Math.abs(this.y - otherCar.y) < this.height * 0.8f;

                if (xOverlap && yOverlap) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCollision(Person person) {
        if (!isActive) return false;

        return x < person.x + person.width &&
            x + width > person.x &&
            y < person.y + person.height &&
            y + height > person.y;
    }
}

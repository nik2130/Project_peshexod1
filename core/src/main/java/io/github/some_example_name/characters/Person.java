package io.github.some_example_name.characters;

import static io.github.some_example_name.Resurces.PERSON0_IMG_PATH;
import static io.github.some_example_name.Resurces.PERSON1_IMG_PATH;
import static io.github.some_example_name.Resurces.PERSON2_IMG_PATH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.github.some_example_name.Config.GameplayConfig;
import io.github.some_example_name.Config.ScreenConfig;

public class Person extends GameObject {
    private boolean isMoving = false;
    private boolean isMovingY = false;
    private boolean isMovingYM = false;
    private int stepAnimationCounter = 0;
    Texture[] framesArray;
    int frameCount;
    int stepsTaken = 0;

    public Person(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        framesArray = new Texture[GameplayConfig.ANIMATION_FRAME_COUNT];
        framesArray[0] = new Texture(PERSON0_IMG_PATH);
        framesArray[1] = new Texture(PERSON1_IMG_PATH);
        framesArray[2] = new Texture(PERSON2_IMG_PATH);
    }

    public void updateTextures() {
        for (Texture texture : framesArray) {
            if (texture != null) {
                texture.dispose();
            }
        }

        framesArray[0] = new Texture(PERSON0_IMG_PATH);
        framesArray[1] = new Texture(PERSON1_IMG_PATH);
        framesArray[2] = new Texture(PERSON2_IMG_PATH);
        frameCount = 0;
    }

    public void move() {
        if (isMoving) {
            stepAnimationCounter++;

            if (stepAnimationCounter >= GameplayConfig.STEP_ANIMATION_FRAMES) {
                isMoving = false;
                stepAnimationCounter = 0;
                stepsTaken += 1;
            }
        }
        if (isMovingY) {
            stepAnimationCounter++;
            if (y < ScreenConfig.SCR_HEIGHT - 100) {
                y += GameplayConfig.PERSON_MOVE_Y_SPEED;
            }

            if (stepAnimationCounter >= GameplayConfig.STEP_ANIMATION_FRAMES) {
                isMovingY = false;
                stepAnimationCounter = 0;
            }
        }
        if (isMovingYM) {
            stepAnimationCounter++;
            if (y > 0) {
                y -= GameplayConfig.PERSON_MOVE_Y_SPEED;
            }

            if (stepAnimationCounter >= GameplayConfig.STEP_ANIMATION_FRAMES) {
                isMovingYM = false;
                stepAnimationCounter = 0;
            }
        }
    }

    public void onClick() {
        if (!isMoving) {
            isMoving = true;
        }
    }

    public void onClickY() {
        if (!isMovingY) {
            isMovingY = true;
        }
    }

    public void onClickYM() {
        if (!isMovingYM) {
            isMovingYM = true;
        }
    }

    @Override
    public void draw(Batch batch) {
        if (isMoving || isMovingY || isMovingYM) {
            int frameIndex = (frameCount / GameplayConfig.ANIMATION_FRAME_DIVISOR) % framesArray.length;
            batch.draw(framesArray[frameIndex], x, y, width, height);
            frameCount++;
        } else {
            batch.draw(framesArray[0], x, y, width, height);
        }
    }

    @Override
    public void dispose() {
        for (Texture texture : framesArray) {
            texture.dispose();
        }
    }

    public int getStepsTaken() {
        return stepsTaken;
    }

    public boolean isMoving() {
        return isMoving;
    }
}

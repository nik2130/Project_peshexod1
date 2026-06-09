package io.github.some_example_name.Game;

import static io.github.some_example_name.Config.ScreenConfig.SCR_HEIGHT;
import static io.github.some_example_name.Config.ScreenConfig.SCR_WIDTH;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.Config.ColorConfig;
import io.github.some_example_name.Config.ScreenConfig;
import io.github.some_example_name.Config.UiConfig;
import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.characters.Car;
import io.github.some_example_name.characters.GameObject;
import io.github.some_example_name.characters.Hatch;
import io.github.some_example_name.characters.Person;
import io.github.some_example_name.characters.Shawarma;
import io.github.some_example_name.components.EatCounter;
import io.github.some_example_name.components.PointCounter;

public class GameRender {
    public PointCounter pointCounter;
    public EatCounter eatCounter;
    public BitmapFont pauseFont;

    private final Array<DrawableItem> drawableItems = new Array<>();

    public GameRender() {
        pointCounter = new PointCounter(
            SCR_WIDTH - UiConfig.POINT_COUNTER_MARGIN_RIGHT - 100,
            SCR_HEIGHT - UiConfig.POINT_COUNTER_MARGIN_TOP);
        eatCounter = new EatCounter(
            SCR_WIDTH - UiConfig.POINT_COUNTER_MARGIN_RIGHT - UiConfig.EAT_COUNTER_MARGIN_RIGHT,
            SCR_HEIGHT - UiConfig.POINT_COUNTER_MARGIN_TOP);
        pauseFont = new BitmapFont();
        pauseFont.getData().setScale(ScreenConfig.FONT_SCALE_PAUSE);
    }

    public void render(Main main, GameWorld world, GameInput input) {
        main.camera.position.set((float) SCR_WIDTH / 2, (float) SCR_HEIGHT / 2, 0);
        main.camera.update();

        main.batch.setProjectionMatrix(main.camera.combined);

        ScreenUtils.clear(ColorConfig.GAME_CLEAR_R, ColorConfig.GAME_CLEAR_G, ColorConfig.GAME_CLEAR_B, 1f);
        main.batch.begin();

        world.background.onDraw(main.batch);

        drawableItems.clear();

        Person person = world.person;
        drawableItems.add(new DrawableItem(person, person.y, 1));

        for (Hatch hatch1 : world.lukes) {
            if (hatch1.isActive) {
                drawableItems.add(new DrawableItem(hatch1, hatch1.y, 2));
            }
        }

        for (Shawarma shawarma1 : world.shawarma) {
            if (shawarma1.isActive) {
                drawableItems.add(new DrawableItem(shawarma1, shawarma1.y, 3));
            }
        }

        for (Car car : world.cars) {
            if (car.isActive) {
                drawableItems.add(new DrawableItem(car, car.y + 1000, 4));
            }
        }

        drawableItems.sort((o1, o2) -> {
            if (o1.layer != o2.layer) {
                return Integer.compare(o1.layer, o2.layer);
            }
            return Float.compare(o1.drawY, o2.drawY);
        });

        for (DrawableItem item : drawableItems) {
            item.draw(main.batch);
        }

        pointCounter.draw(main.batch, "Count: ", world.gamePoints);
        eatCounter.draw(main.batch, world.eat);

        if (world.gameState == GameState.PLAYING) {
            input.pauseButton.draw(main.batch);
        }

        if (world.gameState == GameState.PAUSED) {
            main.batch.setColor(0, 0, 0, 0.7f);
            main.batch.draw(Resurces.whitePixel, 0, 0, SCR_WIDTH, SCR_HEIGHT);
            main.batch.setColor(1, 1, 1, 1);

            String pauseText = "PAUSED";
            pauseFont.draw(main.batch, pauseText, (float) SCR_WIDTH / 2 - 60, SCR_HEIGHT - 100);

            input.drawPauseMenu(main.batch);
        }

        if (world.gameState == GameState.PLAYING) {
            input.drawMovementButtons(main.batch);
        }

        main.batch.end();
    }

    public void dispose() {
        pointCounter.dispose();
        eatCounter.dispose();
        pauseFont.dispose();
    }

    private static class DrawableItem {
        private final GameObject object;
        private final float drawY;
        private final int layer;

        DrawableItem(GameObject object, float drawY, int layer) {
            this.object = object;
            this.drawY = drawY;
            this.layer = layer;
        }

        void draw(com.badlogic.gdx.graphics.g2d.Batch batch) {
            object.draw(batch);
        }
    }
}

package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.GameSettings;
import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.PointCounter;
import io.github.some_example_name.components.TextButton;

public class ScreenRestart implements Screen {
    Main main;
    MovingBackground background;
    TextButton buttonRestart;
    TextButton buttonMenu;
    PointCounter pointCounter;
    int gamePoints;

    public ScreenRestart(Main main) {
        this.main = main;
        background = new MovingBackground(Resurces.PATH_BG_RESTART);
        buttonRestart = new TextButton(GameSettings.RESTART_BTN_X, GameSettings.RESTART_BTN_Y, "RESTART");
        buttonMenu = new TextButton(GameSettings.RESTART_MENU_BTN_X, GameSettings.RESTART_MENU_BTN_Y, "MENU");
        pointCounter = new PointCounter(GameSettings.RESTART_COUNTER_X, GameSettings.RESTART_COUNTER_Y);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(GameSettings.CLEAR_COLOR_R, GameSettings.CLEAR_COLOR_G, GameSettings.CLEAR_COLOR_B, GameSettings.CLEAR_COLOR_A);
        main.camera.update();
        main.batch.setProjectionMatrix(main.camera.combined);
        main.batch.begin();

        if (Gdx.input.justTouched()) {
            Vector3 touch = main.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (buttonRestart.isHint((int) touch.x, (int) touch.y)) {
                Resurces.loseSound.stop();
                main.setScreen(main.screenGame);
            }
            if (buttonMenu.isHint((int) touch.x, (int) touch.y)) {
                Resurces.loseSound.stop();
                main.setScreen(main.screenMenu);
            }
        }

        background.onDraw(main.batch);
        buttonRestart.draw(main.batch);
        buttonMenu.draw(main.batch);
        pointCounter.draw(main.batch, gamePoints);

        main.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        background.dispose();
        buttonRestart.dispose();
        buttonMenu.dispose();
        pointCounter.dispose();
    }
}

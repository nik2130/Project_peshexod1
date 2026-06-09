package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.Config.ColorConfig;
import io.github.some_example_name.Config.UiConfig;
import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.PointCounter;
import io.github.some_example_name.components.TextButton;

public class ScreenMenu extends ScreenAdapter {
    Main main;
    MovingBackground background;
    TextButton buttonStart;
    TextButton buttonSettings;
    TextButton buttonQuit;
    MovingBackground logo;
    PointCounter totalScoreCounter;
    PointCounter highScoreCounter;

    public ScreenMenu(Main main) {
        this.main = main;
        background = new MovingBackground(Resurces.PATH_BG_RESTART);
        buttonStart = new TextButton(UiConfig.MENU_START_BTN_X, UiConfig.MENU_START_BTN_Y, "START");
        buttonSettings = new TextButton(UiConfig.MENU_SETTINGS_BTN_X, UiConfig.MENU_SETTINGS_BTN_Y, "SETTINGS");

        totalScoreCounter = new PointCounter(UiConfig.MENU_COUNTER_MARGIN_LEFT, UiConfig.MENU_COUNTER_MARGIN_BOTTOM);
        highScoreCounter = new PointCounter(UiConfig.MENU_COUNTER_MARGIN_LEFT, UiConfig.MENU_COUNTER_MARGIN_BOTTOM + UiConfig.MENU_COUNTER_SPACING);

        buttonQuit = new TextButton(UiConfig.MENU_QUIT_BTN_X, UiConfig.MENU_QUIT_BTN_Y, "QUIT");
        logo = new MovingBackground(Resurces.PATH_LOGO);
    }

    @Override
    public void show() {
        Resurces.menuMusic.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(ColorConfig.CLEAR_COLOR_R, ColorConfig.CLEAR_COLOR_G, ColorConfig.CLEAR_COLOR_B, ColorConfig.CLEAR_COLOR_A);
        main.camera.update();
        main.batch.setProjectionMatrix(main.camera.combined);
        main.batch.begin();

        if (Gdx.input.justTouched()) {
            Vector3 touch = main.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (buttonQuit.isHint((int) touch.x, (int) touch.y)) {
                Gdx.app.exit();
            }
            if (buttonSettings.isHint((int) touch.x, (int) touch.y)) {
                main.setScreen(main.screenSettings);
            }
            if (buttonStart.isHint((int) touch.x, (int) touch.y)) {
                Resurces.menuMusic.stop();
                main.setScreen(main.screenGame);
            }
        }

        background.onDraw(main.batch);
        logo.onDraw(main.batch);
        buttonStart.draw(main.batch);
        buttonSettings.draw(main.batch);
        buttonQuit.draw(main.batch);

        highScoreCounter.draw(main.batch, "Best: ", main.scoreManager.getHighScore());
        totalScoreCounter.draw(main.batch, "Total: ", main.scoreManager.getTotalScore());

        main.batch.end();
    }



    @Override
    public void dispose() {
        background.dispose();
        buttonSettings.dispose();
        buttonQuit.dispose();
        buttonStart.dispose();
        totalScoreCounter.dispose();
        highScoreCounter.dispose();
    }
}

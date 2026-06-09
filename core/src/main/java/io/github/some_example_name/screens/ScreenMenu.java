package io.github.some_example_name.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.PointCounter;
import io.github.some_example_name.components.TextButton;

public class ScreenMenu implements Screen {
    Main main;
    MovingBackground background;
    TextButton buttonStart;
    TextButton buttonSettings;
    TextButton buttonQuit;
    MovingBackground logo;
    PointCounter totalScoreCounter;
    PointCounter highScoreCounter;
    final int counterMarginLeft = 100;
    final int counterMarginBottom = 100;
    final int counterSpacing = 70;

    public ScreenMenu(Main main) {
        this.main = main;
        background = new MovingBackground(Resurces.PATH_BG_RESTART);
        buttonStart = new TextButton(440, 300, "START");
        buttonSettings = new TextButton(440, 180, "SETTINGS");

        totalScoreCounter = new PointCounter(counterMarginLeft, counterMarginBottom);

        highScoreCounter = new PointCounter(counterMarginLeft, counterMarginBottom + counterSpacing);

        buttonQuit = new TextButton(440, 50, "QUIT");
        logo = new MovingBackground(Resurces.PATH_LOGO);
    }

    @Override
    public void show() {
        Resurces.menuMusic.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
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
        buttonSettings.dispose();
        buttonQuit.dispose();
        buttonStart.dispose();
        totalScoreCounter.dispose();
        highScoreCounter.dispose();
    }
}

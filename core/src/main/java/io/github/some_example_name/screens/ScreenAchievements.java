package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.GameSettings;
import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.TextButton;

public class ScreenAchievements implements Screen {
    Main main;
    MovingBackground background;
    TextButton buttonQuit;
    BitmapFont titleFont;
    BitmapFont descFont;

    boolean ach1000Unlocked = false;
    boolean achCactusUnlocked = false;
    boolean achSnowmanUnlocked = false;

    public ScreenAchievements(Main main) {
        this.main = main;
        background = new MovingBackground(Resurces.PATH_BG_RESTART);
        buttonQuit = new TextButton(GameSettings.ACH_BACK_BTN_X, GameSettings.ACH_BACK_BTN_Y, "BACK");

        titleFont = new BitmapFont();
        titleFont.getData().setScale(GameSettings.FONT_SCALE_TITLE);
        descFont = new BitmapFont();
        descFont.getData().setScale(GameSettings.FONT_SCALE_DESC);
    }

    @Override
    public void show() {
        ach1000Unlocked = main.scoreManager.getHighScore() >= GameSettings.ACHIEVEMENT_WALK_STEPS;
        achCactusUnlocked = main.scoreManager.getHighScore() >= GameSettings.ACHIEVEMENT_DESERT_SCORE;
        achSnowmanUnlocked = main.scoreManager.getHighScore() >= GameSettings.ACHIEVEMENT_WINTER_SCORE;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(GameSettings.CLEAR_COLOR_R, GameSettings.CLEAR_COLOR_G, GameSettings.CLEAR_COLOR_B, GameSettings.CLEAR_COLOR_A);
        main.camera.update();
        main.batch.setProjectionMatrix(main.camera.combined);
        main.batch.begin();

        if (Gdx.input.justTouched()) {
            Vector3 touch = main.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (buttonQuit.isHint((int) touch.x, (int) touch.y)) {
                main.setScreen(main.screenSettings);
            }
        }

        background.onDraw(main.batch);

        titleFont.draw(main.batch, "ACHIEVEMENTS", GameSettings.ACH_TITLE_X, GameSettings.ACH_TITLE_Y);

        float y = GameSettings.ACH_BADGE_ROW_1_Y;
        main.batch.draw(Resurces.ach1000Badge, GameSettings.ACH_BADGE_X, y - GameSettings.ACH_BADGE_SIZE / 2, GameSettings.ACH_BADGE_SIZE, GameSettings.ACH_BADGE_SIZE);
        main.batch.setColor(ach1000Unlocked ? 1 : GameSettings.ACH_LOCKED_ALPHA, ach1000Unlocked ? 1 : GameSettings.ACH_LOCKED_ALPHA, ach1000Unlocked ? 1 : GameSettings.ACH_LOCKED_ALPHA, ach1000Unlocked ? 1 : GameSettings.ACH_LOCKED_DIM);
        main.batch.draw(Resurces.ach1000Badge, GameSettings.ACH_BADGE_X, y - GameSettings.ACH_BADGE_SIZE / 2, GameSettings.ACH_BADGE_SIZE, GameSettings.ACH_BADGE_SIZE);
        main.batch.setColor(1, 1, 1, 1);
        descFont.draw(main.batch, "Walk 1000 steps", GameSettings.ACH_TEXT_X, y + 20);
        descFont.draw(main.batch, ach1000Unlocked ? "[ DONE ]" : "[ " + main.scoreManager.getHighScore() + " / " + GameSettings.ACHIEVEMENT_WALK_STEPS + " ]", GameSettings.ACH_TEXT_X, y - 10);

        y = GameSettings.ACH_BADGE_ROW_2_Y;
        main.batch.draw(Resurces.achCactusBadge, GameSettings.ACH_BADGE_X, y - GameSettings.ACH_BADGE_SIZE / 2, GameSettings.ACH_BADGE_SIZE, GameSettings.ACH_BADGE_SIZE);
        main.batch.setColor(achCactusUnlocked ? 1 : GameSettings.ACH_LOCKED_ALPHA, achCactusUnlocked ? 1 : GameSettings.ACH_LOCKED_ALPHA, achCactusUnlocked ? 1 : GameSettings.ACH_LOCKED_ALPHA, achCactusUnlocked ? 1 : GameSettings.ACH_LOCKED_DIM);
        main.batch.draw(Resurces.achCactusBadge, GameSettings.ACH_BADGE_X, y - GameSettings.ACH_BADGE_SIZE / 2, GameSettings.ACH_BADGE_SIZE, GameSettings.ACH_BADGE_SIZE);
        main.batch.setColor(1, 1, 1, 1);
        descFont.draw(main.batch, "Reach desert (300 pts)", GameSettings.ACH_TEXT_X, y + 20);
        descFont.draw(main.batch, achCactusUnlocked ? "[ DONE ]" : "[ locked ]", GameSettings.ACH_TEXT_X, y - 10);

        y = GameSettings.ACH_BADGE_ROW_3_Y;
        main.batch.draw(Resurces.achSnowmanBadge, GameSettings.ACH_BADGE_X, y - GameSettings.ACH_BADGE_SIZE / 2, GameSettings.ACH_BADGE_SIZE, GameSettings.ACH_BADGE_SIZE);
        main.batch.setColor(achSnowmanUnlocked ? 1 : GameSettings.ACH_LOCKED_ALPHA, achSnowmanUnlocked ? 1 : GameSettings.ACH_LOCKED_ALPHA, achSnowmanUnlocked ? 1 : GameSettings.ACH_LOCKED_ALPHA, achSnowmanUnlocked ? 1 : GameSettings.ACH_LOCKED_DIM);
        main.batch.draw(Resurces.achSnowmanBadge, GameSettings.ACH_BADGE_X, y - GameSettings.ACH_BADGE_SIZE / 2, GameSettings.ACH_BADGE_SIZE, GameSettings.ACH_BADGE_SIZE);
        main.batch.setColor(1, 1, 1, 1);
        descFont.draw(main.batch, "Reach winter (600 pts)", GameSettings.ACH_TEXT_X, y + 20);
        descFont.draw(main.batch, achSnowmanUnlocked ? "[ DONE ]" : "[ locked ]", GameSettings.ACH_TEXT_X, y - 10);

        buttonQuit.draw(main.batch);
        main.batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        background.dispose();
        buttonQuit.dispose();
        titleFont.dispose();
        descFont.dispose();
    }
}

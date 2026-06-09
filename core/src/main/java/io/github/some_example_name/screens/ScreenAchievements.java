package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

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
        buttonQuit = new TextButton(440, 60, "BACK");

        titleFont = new BitmapFont();
        titleFont.getData().setScale(3f);
        descFont = new BitmapFont();
        descFont.getData().setScale(1.5f);
    }

    @Override
    public void show() {
        ach1000Unlocked = main.scoreManager.getHighScore() >= 1000;
        achCactusUnlocked = main.scoreManager.getHighScore() >= 400;
        achSnowmanUnlocked = main.scoreManager.getHighScore() >= 800;
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
                main.setScreen(main.screenSettings);
            }
        }

        background.onDraw(main.batch);

        titleFont.draw(main.batch, "ACHIEVEMENTS", 400, 660);

        float y = 500;
        main.batch.draw(Resurces.ach1000Badge, 80, y - 50, 80, 80);
        main.batch.setColor(ach1000Unlocked ? 1 : 0.5f, ach1000Unlocked ? 1 : 0.5f, ach1000Unlocked ? 1 : 0.5f, ach1000Unlocked ? 1 : 0.6f);
        main.batch.draw(Resurces.ach1000Badge, 80, y - 50, 80, 80);
        main.batch.setColor(1, 1, 1, 1);
        descFont.draw(main.batch, "Walk 1000 steps", 180, y + 20);
        descFont.draw(main.batch, ach1000Unlocked ? "[ DONE ]" : "[ " + main.scoreManager.getHighScore() + " / 1000 ]", 180, y - 10);

        y = 350;
        main.batch.draw(Resurces.achCactusBadge, 80, y - 50, 80, 80);
        main.batch.setColor(achCactusUnlocked ? 1 : 0.5f, achCactusUnlocked ? 1 : 0.5f, achCactusUnlocked ? 1 : 0.5f, achCactusUnlocked ? 1 : 0.6f);
        main.batch.draw(Resurces.achCactusBadge, 80, y - 50, 80, 80);
        main.batch.setColor(1, 1, 1, 1);
        descFont.draw(main.batch, "Reach desert (300 pts)", 180, y + 20);
        descFont.draw(main.batch, achCactusUnlocked ? "[ DONE ]" : "[ locked ]", 180, y - 10);

        y = 200;
        main.batch.draw(Resurces.achSnowmanBadge, 80, y - 50, 80, 80);
        main.batch.setColor(achSnowmanUnlocked ? 1 : 0.5f, achSnowmanUnlocked ? 1 : 0.5f, achSnowmanUnlocked ? 1 : 0.5f, achSnowmanUnlocked ? 1 : 0.6f);
        main.batch.draw(Resurces.achSnowmanBadge, 80, y - 50, 80, 80);
        main.batch.setColor(1, 1, 1, 1);
        descFont.draw(main.batch, "Reach winter (600 pts)", 180, y + 20);
        descFont.draw(main.batch, achSnowmanUnlocked ? "[ DONE ]" : "[ locked ]", 180, y - 10);

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
        titleFont.dispose();
        descFont.dispose();
    }
}

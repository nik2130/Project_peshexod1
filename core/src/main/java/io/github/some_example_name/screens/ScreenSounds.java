package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.TextButton;

public class ScreenSounds implements Screen {
    Main main;
    MovingBackground background;
    TextButton buttonVolumeplus;
    TextButton buttonVolumeminus;
    TextButton buttonQuit;
    private BitmapFont font;
    private Texture whitePixel;

    public ScreenSounds(Main main) {
        this.main = main;
        background = new MovingBackground("backgrounds/restart_bg.png");
        buttonVolumeplus = new TextButton(640, 250, "Volume++");
        buttonVolumeminus = new TextButton(250, 250, "Volume--");
        buttonQuit = new TextButton(440, 100, "BACK");
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whitePixel = new Texture(pixmap);
        pixmap.dispose();
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
            if (buttonVolumeplus.isHint((int) touch.x, (int) touch.y)) {
                Resurces.updateVolume(Resurces.Volume + 0.1f);
            }
            if (buttonVolumeminus.isHint((int) touch.x, (int) touch.y)) {
                Resurces.updateVolume(Resurces.Volume - 0.1f);
            }
            if (buttonQuit.isHint((int) touch.x, (int) touch.y)) {
                main.setScreen(main.screenSettings);
            }

        }


        background.onDraw(main.batch);
        buttonVolumeplus.draw(main.batch);
        buttonVolumeminus.draw(main.batch);
        buttonQuit.draw(main.batch);


        float barWidth = 400;
        float barHeight = 30;
        float barX = 440;
        float barY = 500;

        main.batch.setColor(0.3f, 0.3f, 0.3f, 1);
        main.batch.draw(whitePixel, barX, barY, barWidth, barHeight);

        main.batch.setColor(0.6f, 0f, 0f, 1);
        float fillWidth = barWidth * Resurces.Volume;
        main.batch.draw(whitePixel, barX, barY, fillWidth, barHeight);

        main.batch.setColor(1, 1, 1, 1);

        font.draw(main.batch, "Volume: " + Resurces.getVolumePercent(), 450, 525);

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
        font.dispose();
    }
}

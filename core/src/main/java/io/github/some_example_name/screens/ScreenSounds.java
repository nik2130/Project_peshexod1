package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.Config.AudioConfig;
import io.github.some_example_name.Config.ColorConfig;
import io.github.some_example_name.Config.ScreenConfig;
import io.github.some_example_name.Config.UiConfig;
import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.TextButton;

public class ScreenSounds extends ScreenAdapter {
    Main main;
    MovingBackground background;
    TextButton buttonVolumeplus;
    TextButton buttonVolumeminus;
    TextButton buttonQuit;
    private BitmapFont font;
    public boolean fromGame = false;

    public ScreenSounds(Main main) {
        this.main = main;
        background = new MovingBackground(Resurces.PATH_BG_RESTART);
        buttonVolumeplus = new TextButton(UiConfig.SOUND_PLUS_BTN_X, UiConfig.SOUND_PLUS_BTN_Y, "Volume++");
        buttonVolumeminus = new TextButton(UiConfig.SOUND_MINUS_BTN_X, UiConfig.SOUND_MINUS_BTN_Y, "Volume--");
        buttonQuit = new TextButton(UiConfig.SOUND_BACK_BTN_X, UiConfig.SOUND_BACK_BTN_Y, "BACK");
        font = new BitmapFont();
        font.getData().setScale(ScreenConfig.FONT_SCALE_SOUND);
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
            if (buttonVolumeplus.isHint((int) touch.x, (int) touch.y)) {
                Resurces.updateVolume(Resurces.Volume + AudioConfig.VOLUME_STEP);
            }
            if (buttonVolumeminus.isHint((int) touch.x, (int) touch.y)) {
                Resurces.updateVolume(Resurces.Volume - AudioConfig.VOLUME_STEP);
            }
            if (buttonQuit.isHint((int) touch.x, (int) touch.y)) {
                if (fromGame) {
                    fromGame = false;
                    main.setScreen(main.screenGame);
                } else {
                    main.setScreen(main.screenSettings);
                }
            }
        }

        background.onDraw(main.batch);
        buttonVolumeplus.draw(main.batch);
        buttonVolumeminus.draw(main.batch);
        buttonQuit.draw(main.batch);

        float barWidth = ScreenConfig.VOLUME_BAR_WIDTH;
        float barHeight = ScreenConfig.VOLUME_BAR_HEIGHT;
        float barX = UiConfig.VOLUME_BAR_X;
        float barY = UiConfig.VOLUME_BAR_Y;

        main.batch.setColor(ColorConfig.VOLUME_BAR_BG_R, ColorConfig.VOLUME_BAR_BG_G, ColorConfig.VOLUME_BAR_BG_B, ColorConfig.VOLUME_BAR_BG_A);
        main.batch.draw(Resurces.whitePixel, barX, barY, barWidth, barHeight);

        main.batch.setColor(ColorConfig.VOLUME_BAR_FILL_R, ColorConfig.VOLUME_BAR_FILL_G, ColorConfig.VOLUME_BAR_FILL_B, ColorConfig.VOLUME_BAR_FILL_A);
        float fillWidth = barWidth * Resurces.Volume;
        main.batch.draw(Resurces.whitePixel, barX, barY, fillWidth, barHeight);

        main.batch.setColor(1, 1, 1, 1);

        font.draw(main.batch, "Volume: " + Resurces.getVolumePercent(), UiConfig.VOLUME_LABEL_X, UiConfig.VOLUME_LABEL_Y);

        main.batch.end();
    }


    @Override
    public void dispose() {
        background.dispose();
        buttonVolumeplus.dispose();
        buttonVolumeminus.dispose();
        buttonQuit.dispose();
        font.dispose();
    }
}

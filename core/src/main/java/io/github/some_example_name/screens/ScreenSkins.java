package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.GameSettings;
import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.TextButton;

public class ScreenSkins implements Screen {

    Main main;
    MovingBackground background;
    TextButton buttonSkin1;
    TextButton buttonSkin2;
    TextButton buttonSkin3;
    TextButton buttonQuit;

    public ScreenSkins(Main main) {
        this.main = main;
        background = new MovingBackground(Resurces.PATH_BG_RESTART);
        buttonSkin1 = new TextButton(GameSettings.SKINS_BTN_X, GameSettings.SKINS_BTN_1_Y, "Human");
        buttonSkin2 = new TextButton(GameSettings.SKINS_BTN_X, GameSettings.SKINS_BTN_2_Y, "Gomuncul");
        buttonSkin3 = new TextButton(GameSettings.SKINS_BTN_X, GameSettings.SKINS_BTN_3_Y, "WtfFf']qaw!!11!");
        buttonQuit = new TextButton(GameSettings.SKINS_BACK_BTN_X, GameSettings.SKINS_BACK_BTN_Y, "BACK");
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
            if (buttonSkin1.isHint((int) touch.x, (int) touch.y)) {
                Resurces.skin = "skins/bluebird/blue_birdS.png";
                Resurces.PERSON0_IMG_PATH = "skins/bluebird/blue_bird0.png";
                Resurces.PERSON1_IMG_PATH = "skins/bluebird/blue_bird1.png";
                Resurces.PERSON2_IMG_PATH = "skins/bluebird/blue_bird2.png";
                main.skinChanged = true;
                Resurces.reloadSkinTexture();
            }
            if (buttonSkin2.isHint((int) touch.x, (int) touch.y)) {
                Resurces.skin = "skins/bird2/p1.png";
                Resurces.PERSON0_IMG_PATH = "skins/bird2/p1.png";
                Resurces.PERSON1_IMG_PATH = "skins/bird2/p2.png";
                Resurces.PERSON2_IMG_PATH = "skins/bird2/p3.png";
                Resurces.PERSON3_IMG_PATH = "skins/bird2/p4.png";
                Resurces.PERSON4_IMG_PATH = "skins/bird2/p5.png";
                Resurces.PERSON5_IMG_PATH = "skins/bird2/p6.png";
                Resurces.PERSON6_IMG_PATH = "skins/bird2/p8.png";
                Resurces.PERSON7_IMG_PATH = "skins/bird2/p9.png";
                main.skinChanged = true;
                Resurces.reloadSkinTexture();
            }
            if (buttonSkin3.isHint((int) touch.x, (int) touch.y)) {
                Resurces.skin = "skins/wtfbird/birdS.png";
                Resurces.PERSON0_IMG_PATH = "skins/wtfbird/bird0.png";
                Resurces.PERSON1_IMG_PATH = "skins/wtfbird/bird1.png";
                Resurces.PERSON2_IMG_PATH = "skins/wtfbird/bird2.png";
                main.skinChanged = true;
                Resurces.reloadSkinTexture();
            }
            if (buttonQuit.isHint((int) touch.x, (int) touch.y)) {
                main.setScreen(main.screenSettings);
            }
        }

        background.onDraw(main.batch);
        main.batch.draw(Resurces.skinTexture, GameSettings.SKINS_PREVIEW_X, GameSettings.SKINS_PREVIEW_Y, GameSettings.SKINS_PREVIEW_WIDTH, GameSettings.SKINS_PREVIEW_HEIGHT);
        buttonSkin1.draw(main.batch);
        buttonSkin2.draw(main.batch);
        buttonSkin3.draw(main.batch);
        buttonQuit.draw(main.batch);

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
        buttonSkin1.dispose();
        buttonSkin2.dispose();
        buttonSkin3.dispose();
        buttonQuit.dispose();
    }
}

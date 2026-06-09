package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.screens.ScreenGame;
import io.github.some_example_name.screens.ScreenMenu;
import io.github.some_example_name.screens.ScreenRestart;
import io.github.some_example_name.screens.ScreenSettings;
import io.github.some_example_name.screens.ScreenSkins;
import io.github.some_example_name.screens.ScreenSounds;
import io.github.some_example_name.screens.ScreenAchievements;

public class Main extends Game {

    public ScreenMenu screenMenu;
    public ScreenGame screenGame;
    public ScreenRestart screenRestart;
    public ScreenSettings screenSettings;
    public ScreenSkins screenSkins;
    public ScreenSounds screenSounds;
    public ScreenAchievements screenAchievements;

    public OrthographicCamera camera;
    public SpriteBatch batch;
    public boolean skinChanged = false;

    public ScoreManager scoreManager;

    public static final int SCR_WIDTH = 1280;
    public static final int SCR_HEIGHT = 720;

    @Override
    public void create() {
        Resurces.initMusic();
        Resurces.initTextures();

        scoreManager = new ScoreManager();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        batch = new SpriteBatch();
        screenMenu = new ScreenMenu(this);
        screenGame = new ScreenGame(this);
        screenRestart = new ScreenRestart(this);
        screenSettings = new ScreenSettings(this);
        screenSkins = new ScreenSkins(this);
        screenSounds = new ScreenSounds(this);
        screenAchievements = new ScreenAchievements(this);
        setScreen(screenMenu);
    }

    @Override
    public void dispose() {
        batch.dispose();
        Resurces.disposeMusic();
        Resurces.disposeTextures();
    }
}

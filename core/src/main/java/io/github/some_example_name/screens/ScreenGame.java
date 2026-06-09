package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

import io.github.some_example_name.Game.Biome;
import io.github.some_example_name.Game.GameInput;
import io.github.some_example_name.Game.GameRender;
import io.github.some_example_name.Game.GameState;
import io.github.some_example_name.Game.GameWorld;
import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;

public class ScreenGame extends ScreenAdapter {
    Main main;
    public GameWorld world;
    public GameInput input;
    public GameRender renderer;
    public static int gamePoints;

    public ScreenGame(Main main) {
        this.main = main;
        world = new GameWorld();
        input = new GameInput();
        renderer = new GameRender();
    }

    @Override
    public void show() {
        world.init(main);
        input.initMovementButtons();
    }

    @Override
    public void render(float delta) {
        if (main.skinChanged) {
            world.person.updateTextures();
            main.skinChanged = false;
        }

        input.handle(main, world);

        if (world.gameState == GameState.PLAYING) {
            world.update(delta);
        } else if (world.gameState == GameState.GAME_OVER) {
            gamePoints = world.gamePoints;
            world.handleGameOver(main);
            return;
        }

        renderer.render(main, world, input);
    }

    @Override
    public void pause() {
        if (world.gameState == GameState.PLAYING) {
            world.gameState = GameState.PAUSED;
            Resurces.gameMusic.pause();
        }
    }

    @Override
    public void resume() {
        if (world.gameState == GameState.PAUSED) {
            world.gameState = GameState.PLAYING;
            if (!Resurces.gameMusic.isPlaying()) {
                Resurces.gameMusic.play();
            }
        }
    }

    @Override
    public void hide() {
        Resurces.gameMusic.stop();
    }

    @Override
    public void dispose() {
        world.dispose();
        input.dispose();
        renderer.dispose();
    }
}

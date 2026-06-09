package io.github.some_example_name.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;

import io.github.some_example_name.Config.ScreenConfig;
import io.github.some_example_name.Config.UiConfig;
import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.components.ArrowButton;
import io.github.some_example_name.components.TextButton;

public class GameInput {
    public final TextButton pauseButton;
    public TextButton resumeButton;
    public TextButton restartButton;
    public TextButton menuButton;
    private TextButton pauseSoundBtn;
    public ArrowButton buttonUp;
    public ArrowButton buttonDown;
    public ArrowButton buttonJump;

    public final Vector3 touchPoint = new Vector3();

    private final int BUTTON_SIZE;

    public GameInput() {
        BUTTON_SIZE = ScreenConfig.BUTTON_SIZE;

        pauseButton = createPauseButton();
        setupPauseMenuButtons();
    }

    private TextButton createPauseButton() {
        TextButton button = new TextButton(1160, 640, "II");
        button.font.getData().setScale(ScreenConfig.PAUSE_BTN_SCALE);

        com.badlogic.gdx.graphics.g2d.GlyphLayout layout =
            new com.badlogic.gdx.graphics.g2d.GlyphLayout(button.font, "II");
        button.textWidth = (int) layout.width;
        button.textHeight = (int) layout.height;
        button.buttonWidth = 60;
        button.buttonHeight = 60;
        button.textX = button.x + (button.buttonWidth - button.textWidth) / 2;
        button.textY = button.y + (button.buttonHeight + button.textHeight) / 2;

        return button;
    }

    private void setupPauseMenuButtons() {
        int SCR_WIDTH = ScreenConfig.SCR_WIDTH;
        int SCR_HEIGHT = ScreenConfig.SCR_HEIGHT;

        int centerX = SCR_WIDTH / 2;
        int centerY = SCR_HEIGHT / 2;

        int BUTTON_OFFSET_X = ScreenConfig.PAUSE_BUTTON_OFFSET_X;
        int buttonX = centerX - BUTTON_OFFSET_X;
        int PAUSE_BUTTON_SPACING = ScreenConfig.PAUSE_BUTTON_SPACING;
        int BUTTON_HEIGHT = ScreenConfig.PAUSE_BUTTON_HEIGHT;
        int totalHeight = 4 * BUTTON_HEIGHT + 3 * PAUSE_BUTTON_SPACING;
        int startY = centerY + totalHeight / 2;

        int resumeY = startY;
        int restartY = startY - BUTTON_HEIGHT - PAUSE_BUTTON_SPACING;
        int soundY = restartY - BUTTON_HEIGHT - PAUSE_BUTTON_SPACING;
        int menuY = soundY - BUTTON_HEIGHT - PAUSE_BUTTON_SPACING;

        resumeButton = new TextButton(buttonX, resumeY, "RESUME");
        restartButton = new TextButton(buttonX, restartY, "RESTART");
        pauseSoundBtn = new TextButton(buttonX, soundY, "SOUND");
        menuButton = new TextButton(buttonX, menuY, "MENU");
    }

    public void initMovementButtons() {
        buttonJump = new ArrowButton(UiConfig.WALK_JUMP_X, UiConfig.WALK_JUMP_Y, "", Resurces.PATH_RIGHT);
        buttonJump.buttonWidth = BUTTON_SIZE;
        buttonJump.buttonHeight = BUTTON_SIZE;
        buttonJump.textX = buttonJump.x + (BUTTON_SIZE - buttonJump.textWidth) / 2;
        buttonJump.textY = buttonJump.y + BUTTON_SIZE / 2 + buttonJump.textHeight / 2;
        buttonJump.font.getData().setScale(ScreenConfig.FONT_SCALE_WALK);

        buttonUp = new ArrowButton(UiConfig.WALK_UP_X, UiConfig.WALK_UP_Y, "", Resurces.PATH_UP);
        buttonUp.buttonWidth = BUTTON_SIZE;
        buttonUp.buttonHeight = BUTTON_SIZE;
        buttonUp.textX = buttonUp.x + (BUTTON_SIZE - buttonUp.textWidth) / 2;
        buttonUp.textY = buttonUp.y + BUTTON_SIZE / 2 + buttonUp.textHeight / 2;
        buttonUp.font.getData().setScale(ScreenConfig.FONT_SCALE_WALK);

        buttonDown = new ArrowButton(UiConfig.WALK_DOWN_X, UiConfig.WALK_DOWN_Y, "", Resurces.PATH_DOWN);
        buttonDown.buttonWidth = BUTTON_SIZE;
        buttonDown.buttonHeight = BUTTON_SIZE;
        buttonDown.textX = buttonDown.x + (BUTTON_SIZE - buttonDown.textWidth) / 2;
        buttonDown.textY = buttonDown.y + BUTTON_SIZE / 2 + buttonDown.textHeight / 2;
        buttonDown.font.getData().setScale(ScreenConfig.FONT_SCALE_WALK);
    }

    public void handle(Main main, GameWorld world) {
        if (!Gdx.input.justTouched()) return;

        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        main.camera.unproject(touchPoint);

        if (world.gameState == GameState.PLAYING) {
            handlePlaying(main, world);
        } else if (world.gameState == GameState.PAUSED) {
            handlePaused(main, world);
        }
    }

    private void handlePlaying(Main main, GameWorld world) {
        if (pauseButton.isHint((int) touchPoint.x, (int) touchPoint.y)) {
            world.gameState = GameState.PAUSED;
            Resurces.gameMusic.pause();
            return;
        }

        if (world.isDesertUnlocked()) {
            world.switchBiomeDesert();
        }
        if (world.isWinterUnlocked()) {
            world.switchBiomeWinter();
        }

        if (buttonUp.isHint((int) touchPoint.x, (int) touchPoint.y)) {
            world.person.onClickY();
        } else if (buttonDown.isHint((int) touchPoint.x, (int) touchPoint.y)) {
            world.person.onClickYM();
        } else if (buttonJump.isHint((int) touchPoint.x, (int) touchPoint.y)) {
            world.person.onClick();
        }
    }

    private void handlePaused(Main main, GameWorld world) {
        if (resumeButton.isHint((int) touchPoint.x, (int) touchPoint.y)) {
            world.gameState = GameState.PLAYING;
            if (!Resurces.gameMusic.isPlaying()) {
                Resurces.gameMusic.play();
            }
        } else if (restartButton.isHint((int) touchPoint.x, (int) touchPoint.y)) {
            world.init(main);
        } else if (menuButton.isHint((int) touchPoint.x, (int) touchPoint.y)) {
            Resurces.gameMusic.stop();
            main.setScreen(main.screenMenu);
        } else if (pauseSoundBtn.isHint((int) touchPoint.x, (int) touchPoint.y)) {
            main.screenSounds.fromGame = true;
            main.setScreen(main.screenSounds);
        }
    }

    public void drawPauseOverlay(Batch batch) {
        pauseButton.draw(batch);
    }

    public void drawPauseMenu(Batch batch) {
        resumeButton.draw(batch);
        restartButton.draw(batch);
        menuButton.draw(batch);
        pauseSoundBtn.draw(batch);
    }

    public void drawSoundButton(Batch batch) {
        pauseSoundBtn.draw(batch);
    }

    public void drawMovementButtons(Batch batch) {
        if (buttonUp != null) buttonUp.draw(batch);
        if (buttonDown != null) buttonDown.draw(batch);
        if (buttonJump != null) buttonJump.draw(batch);
    }

    public void dispose() {
        pauseButton.dispose();
        resumeButton.dispose();
        restartButton.dispose();
        menuButton.dispose();
        pauseSoundBtn.dispose();
        if (buttonUp != null) buttonUp.dispose();
        if (buttonDown != null) buttonDown.dispose();
        if (buttonJump != null) buttonJump.dispose();
    }
}

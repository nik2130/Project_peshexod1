package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.GameSettings;
import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.TextButton;

public class ScreenSettings implements Screen {
    Main main;
    MovingBackground background;
    TextButton buttonSkins;
    TextButton buttonSounds;
    TextButton buttonAchievements;
    TextButton buttonResetRecords;
    TextButton buttonQuit;

    private BitmapFont notificationFont;
    private String notificationText;
    private float notificationTimer;
    private Texture notificationBackground;
    private int notificationBgWidth = GameSettings.NOTIFICATION_BG_WIDTH;
    private int notificationBgHeight = GameSettings.NOTIFICATION_BG_HEIGHT;

    public ScreenSettings(Main main) {
        this.main = main;
        background = new MovingBackground(Resurces.PATH_BG_RESTART);

        buttonSkins = new TextButton(GameSettings.SETTINGS_BTN_X, GameSettings.SETTINGS_BTN_1_Y, "SKINS");
        buttonSounds = new TextButton(GameSettings.SETTINGS_BTN_X, GameSettings.SETTINGS_BTN_2_Y, "SOUNDS");
        buttonAchievements = new TextButton(GameSettings.SETTINGS_BTN_X, GameSettings.SETTINGS_BTN_3_Y, "ACHIEVEMENTS");
        buttonQuit = new TextButton(GameSettings.SETTINGS_BTN_X, GameSettings.SETTINGS_BTN_4_Y, "BACK");

        buttonResetRecords = createCompactButton(1100, 650, "RESET", GameSettings.FONT_SCALE_TITLE, 180, 70);

        notificationFont = new BitmapFont();
        notificationFont.getData().setScale(GameSettings.FONT_SCALE_NOTIFICATION);
        notificationFont.setColor(Color.WHITE);
        notificationText = "";
        notificationTimer = 0;

        createNotificationBackground();
    }

    private void createNotificationBackground() {
        int borderThickness = GameSettings.NOTIFICATION_BORDER_THICKNESS;
        int totalWidth = notificationBgWidth + borderThickness * 2;
        int totalHeight = notificationBgHeight + borderThickness * 2;

        Pixmap pixmap = new Pixmap(totalWidth, totalHeight, Pixmap.Format.RGBA8888);

        pixmap.setColor(new Color(0, 0, 0, 0));
        pixmap.fill();

        pixmap.setColor(new Color(GameSettings.NOTIF_BORDER_R, GameSettings.NOTIF_BORDER_G, GameSettings.NOTIF_BORDER_B, GameSettings.NOTIF_BORDER_A));
        pixmap.fillRectangle(0, 0, totalWidth, totalHeight);

        pixmap.setColor(new Color(GameSettings.NOTIF_FILL_R, GameSettings.NOTIF_FILL_G, GameSettings.NOTIF_FILL_B, GameSettings.NOTIF_FILL_A));
        pixmap.fillRectangle(
            borderThickness,
            borderThickness,
            notificationBgWidth,
            notificationBgHeight
        );

        notificationBackground = new Texture(pixmap);
        pixmap.dispose();
    }

    private TextButton createCompactButton(int x, int y, String text, float fontScale, int width, int height) {
        TextButton button = new TextButton(x, y, text);

        button.font.getData().setScale(fontScale);

        GlyphLayout layout = new GlyphLayout(button.font, text);
        button.textWidth = (int) layout.width;
        button.textHeight = (int) layout.height;

        button.buttonWidth = width;
        button.buttonHeight = height;

        button.textX = button.x + (button.buttonWidth - button.textWidth) / 2;
        button.textY = button.y + (button.buttonHeight + button.textHeight) / 2;

        return button;
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

        if (notificationTimer > 0) {
            notificationTimer -= delta;
            if (notificationTimer <= 0) {
                notificationText = "";
            }
        }

        if (Gdx.input.justTouched()) {
            Vector3 touch = main.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (buttonSkins.isHint((int) touch.x, (int) touch.y)) {
                main.setScreen(main.screenSkins);
            }
            if (buttonSounds.isHint((int) touch.x, (int) touch.y)) {
                main.setScreen(main.screenSounds);
            }
            if (buttonAchievements.isHint((int) touch.x, (int) touch.y)) {
                main.setScreen(main.screenAchievements);
            }
            if (buttonResetRecords.isHint((int) touch.x, (int) touch.y)) {
                main.scoreManager.resetAllRecords();

                notificationText = "Statistics have been reset!";
                notificationTimer = GameSettings.NOTIFICATION_DURATION;
            }
            if (buttonQuit.isHint((int) touch.x, (int) touch.y)) {
                main.setScreen(main.screenMenu);
            }
        }

        background.onDraw(main.batch);

        buttonSkins.draw(main.batch);
        buttonSounds.draw(main.batch);
        buttonAchievements.draw(main.batch);
        buttonQuit.draw(main.batch);

        buttonResetRecords.draw(main.batch);

        if (notificationTimer > 0) {
            int borderThickness = GameSettings.NOTIFICATION_BORDER_THICKNESS;
            float centerX = main.camera.viewportWidth / 2;
            float centerY = main.camera.viewportHeight / 2;

            int totalWidth = notificationBgWidth + borderThickness * 2;
            int totalHeight = notificationBgHeight + borderThickness * 2;

            main.batch.draw(notificationBackground,
                centerX - totalWidth / 2,
                centerY + 100 - totalHeight / 2,
                totalWidth, totalHeight);

            GlyphLayout layout = new GlyphLayout(notificationFont, notificationText);
            float textX = centerX - layout.width / 2;
            float textY = centerY + 100 + layout.height / 2;

            notificationFont.draw(main.batch, notificationText, textX, textY);
        }

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
        buttonSkins.dispose();
        buttonSounds.dispose();
        buttonAchievements.dispose();
        buttonResetRecords.dispose();
        buttonQuit.dispose();
        notificationFont.dispose();
        if (notificationBackground != null) {
            notificationBackground.dispose();
        }
    }
}

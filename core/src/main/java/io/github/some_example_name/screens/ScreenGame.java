package io.github.some_example_name.screens;

import static io.github.some_example_name.GameSettings.SCR_HEIGHT;
import static io.github.some_example_name.GameSettings.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Array;

import io.github.some_example_name.GameSettings;
import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.characters.GameObject;
import io.github.some_example_name.characters.Hatch;
import io.github.some_example_name.characters.Person;
import io.github.some_example_name.characters.Car;
import io.github.some_example_name.characters.Shawarma;
import io.github.some_example_name.components.EatCounter;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.PointCounter;
import io.github.some_example_name.components.TextButton;
import io.github.some_example_name.components.ArrowButton;

public class ScreenGame extends ScreenAdapter {
    Main main;
    Person person;
    MovingBackground background;
    Hatch[] lukes;
    Car[] cars;
    Shawarma[] shawarma;
    boolean isGameOver;
    int gamePoints;
    PointCounter pointCounter;
    EatCounter eatCounter;
    final int pointCounterMarginRight = GameSettings.POINT_COUNTER_MARGIN_RIGHT;
    final int pointCounterMarginTop = GameSettings.POINT_COUNTER_MARGIN_TOP;
    boolean backdesert = false;
    boolean winter = false;

    int eat = GameSettings.INITIAL_EAT;

    private static Preferences preferences = Gdx.app.getPreferences(GameSettings.USER_SAVES);
    private final int BACKGROUND_SPEED = GameSettings.BACKGROUND_SPEED;

    private Array<DrawableItem> drawableItems = new Array<>();

    private float carSpawnTimer = 0;
    private final float CAR_SPAWN_INTERVAL = GameSettings.CAR_SPAWN_INTERVAL;
    private final float MIN_CAR_DISTANCE = GameSettings.MIN_CAR_DISTANCE;

    private enum GameState {
        PLAYING, PAUSED, GAME_OVER
    }

    private GameState gameState = GameState.PLAYING;

    private TextButton pauseButton;
    private TextButton resumeButton;
    private TextButton restartButton;
    private TextButton menuButton;
    private TextButton pauseSoundBtn;
    private ArrowButton buttonUp;
    private ArrowButton buttonDown;
    private ArrowButton buttonJump;

    private Vector3 touchPoint = new Vector3();

    private BitmapFont pauseFont;

    private final int PAUSE_BUTTON_SPACING = GameSettings.PAUSE_BUTTON_SPACING;
    private final int BUTTON_HEIGHT = GameSettings.PAUSE_BUTTON_HEIGHT;
    private final int BUTTON_OFFSET_X = GameSettings.PAUSE_BUTTON_OFFSET_X;
    private final int BUTTON_OFFSET_Y = GameSettings.PAUSE_BUTTON_OFFSET_Y;

    private final int SAFE_SPAWN_X = GameSettings.SAFE_SPAWN_X;
    private final int SAFE_SPAWN_Y = GameSettings.SAFE_SPAWN_Y;

    private final int TOP_ZONE_HEIGHT = (int) (SCR_HEIGHT * 0.35f);
    private final int BOTTOM_ZONE_HEIGHT = (int) (SCR_HEIGHT * 0.35f);
    private final int BUTTON_SIZE = GameSettings.BUTTON_SIZE;
    private final int BUTTON_MARGIN = GameSettings.BUTTON_MARGIN;
    private final int BUTTONS_POS_X = 80;
    private final int BUTTON_UP_Y = SCR_HEIGHT - BUTTON_SIZE - BUTTON_MARGIN;
    private final int BUTTON_DOWN_Y = BUTTON_MARGIN;

    public ScreenGame(Main main) {
        this.main = main;

        initCars();
        initLukes();
        initShaverm();

        person = new Person(findSafeSpawnPosition(), 100, 20, 100, 200);
        person.updateTextures();

        pointCounter = new PointCounter(SCR_WIDTH - pointCounterMarginRight - 100, SCR_HEIGHT - pointCounterMarginTop);
        eatCounter = new EatCounter(SCR_WIDTH - pointCounterMarginRight - GameSettings.EAT_COUNTER_MARGIN_RIGHT, SCR_HEIGHT - pointCounterMarginTop);
        background = new MovingBackground(Resurces.PATH_BG_ROAD);

        pauseButton = createCompactButton(SCR_WIDTH - 120, SCR_HEIGHT - 80, "II", GameSettings.PAUSE_BTN_SCALE, 60, 60);

        setupPauseMenuButtons();
        initMovementButtons();
        pauseFont = new BitmapFont();
        pauseFont.getData().setScale(GameSettings.FONT_SCALE_PAUSE);
    }

    private TextButton createCompactButton(int x, int y, String text, float fontScale, int width, int height) {
        TextButton button = new TextButton(x, y, text);
        button.font.getData().setScale(fontScale);

        com.badlogic.gdx.graphics.g2d.GlyphLayout layout = new com.badlogic.gdx.graphics.g2d.GlyphLayout(button.font, text);
        button.textWidth = (int) layout.width;
        button.textHeight = (int) layout.height;

        button.buttonWidth = width;
        button.buttonHeight = height;

        button.textX = button.x + (button.buttonWidth - button.textWidth) / 2;
        button.textY = button.y + (button.buttonHeight + button.textHeight) / 2;

        return button;
    }

    private void initMovementButtons() {
        buttonJump = new ArrowButton(GameSettings.WALK_JUMP_X, GameSettings.WALK_JUMP_Y, "", Resurces.PATH_RIGHT);
        buttonJump.buttonWidth = BUTTON_SIZE;
        buttonJump.buttonHeight = BUTTON_SIZE;
        buttonJump.textX = buttonJump.x + (BUTTON_SIZE - buttonJump.textWidth) / 2;
        buttonJump.textY = buttonJump.y + BUTTON_SIZE / 2 + buttonJump.textHeight / 2;
        buttonJump.font.getData().setScale(GameSettings.FONT_SCALE_WALK);

        buttonUp = new ArrowButton(GameSettings.WALK_UP_X, GameSettings.WALK_UP_Y, "", Resurces.PATH_UP);
        buttonUp.buttonWidth = BUTTON_SIZE;
        buttonUp.buttonHeight = BUTTON_SIZE;
        buttonUp.textX = buttonUp.x + (BUTTON_SIZE - buttonUp.textWidth) / 2;
        buttonUp.textY = buttonUp.y + BUTTON_SIZE / 2 + buttonUp.textHeight / 2;
        buttonUp.font.getData().setScale(GameSettings.FONT_SCALE_WALK);

        buttonDown = new ArrowButton(GameSettings.WALK_DOWN_X, GameSettings.WALK_DOWN_Y, "", Resurces.PATH_DOWN);
        buttonDown.buttonWidth = BUTTON_SIZE;
        buttonDown.buttonHeight = BUTTON_SIZE;
        buttonDown.textX = buttonDown.x + (BUTTON_SIZE - buttonDown.textWidth) / 2;
        buttonDown.textY = buttonDown.y + BUTTON_SIZE / 2 + buttonDown.textHeight / 2;
        buttonDown.font.getData().setScale(GameSettings.FONT_SCALE_WALK);
    }

    private int findSafeSpawnPosition() {
        int safeX = SAFE_SPAWN_X;
        int attempts = 0;
        final int safeAreaWidth = GameSettings.SAFE_AREA_WIDTH;

        boolean safePositionFound = false;

        while (!safePositionFound && attempts < GameSettings.SAFE_SPAWN_ATTEMPTS) {
            safePositionFound = true;

            for (Hatch hatch1 : lukes) {
                if (hatch1 != null && hatch1.isActive) {
                    boolean xOverlap = Math.abs(hatch1.x - safeX) < (hatch1.width + safeAreaWidth) / 2;
                    boolean yOverlap = Math.abs(hatch1.y - SAFE_SPAWN_Y) < (hatch1.height + 200) / 2;

                    if (xOverlap && yOverlap) {
                        safePositionFound = false;
                        safeX = (safeX + 150) % (SCR_WIDTH - 200);
                        break;
                    }
                }
            }

            for (Shawarma shawarma1 : shawarma) {
                if (shawarma1 != null && shawarma1.isActive) {
                    boolean xOverlap = Math.abs(shawarma1.x - safeX) < (shawarma1.width + safeAreaWidth) / 2;
                    boolean yOverlap = Math.abs(shawarma1.y - SAFE_SPAWN_Y) < (shawarma1.height + 200) / 2;

                    if (xOverlap && yOverlap) {
                        safePositionFound = false;
                        safeX = (safeX + 150) % (SCR_WIDTH - 200);
                        break;
                    }
                }
            }

            attempts++;
        }

        if (!safePositionFound) {
            return SCR_WIDTH / 3;
        }

        return safeX;
    }

    private void setupPauseMenuButtons() {
        int centerX = SCR_WIDTH / 2;
        int centerY = SCR_HEIGHT / 2;

        int buttonX = centerX - BUTTON_OFFSET_X;
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

    @Override
    public void show() {
        background = new MovingBackground(Resurces.PATH_BG_ROAD);

        Resurces.lukest = Resurces.loadTexture(Resurces.PATH_LUKE);
        Resurces.carTex = Resurces.loadTexture(Resurces.PATH_CAR);
        Resurces.shavermTex = Resurces.loadTexture(Resurces.PATH_SHAVERM);
        isGameOver = false;
        backdesert = false;
        winter = false;

        gamePoints = 0;
        eat = GameSettings.INITIAL_EAT;
        gameState = GameState.PLAYING;

        for (Car car : cars) {
            car.dispose();
        }
        for (Hatch hatch1 : lukes) {
            hatch1.dispose();
        }
        for (Shawarma shawarma1 : shawarma) {
            shawarma1.dispose();
        }

        initCars();
        initLukes();
        initShaverm();

        if (person != null) {
            person.dispose();
        }

        int safeSpawnX = findSafeSpawnPosition();
        person = new Person(safeSpawnX, SAFE_SPAWN_Y, 20, 50, 100);
        person.updateTextures();

        carSpawnTimer = 0;

        checkSpawnSafety();
        initMovementButtons();

        Resurces.gameMusic.play();
    }

    private void checkSpawnSafety() {
        for (Hatch hatch1 : lukes) {
            if (hatch1.isActive && hatch1.isCollision(person)) {
                hatch1.isActive = false;
            }
        }

        for (Shawarma shawarma1 : shawarma) {
            if (shawarma1.isActive && shawarma1.isCollision(person)) {
                shawarma1.isActive = false;
            }
        }
    }

    @Override
    public void render(float delta) {
        if (main.skinChanged) {
            person.updateTextures();
            main.skinChanged = false;
        }

        handleInput();

        if (gameState == GameState.PLAYING) {
            updateGame(delta);
        } else if (gameState == GameState.GAME_OVER) {
            handleGameOver();
            return;
        }

        renderGame();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            main.camera.unproject(touchPoint);

            if (gameState == GameState.PLAYING) {
                if (pauseButton.isHint((int) touchPoint.x, (int) touchPoint.y)) {
                    gameState = GameState.PAUSED;
                    Resurces.gameMusic.pause();
                    return;
                }
                if (gamePoints > GameSettings.DESERT_UNLOCK_SCORE && !backdesert) {
                    background = new MovingBackground(Resurces.PATH_BG_DESERT);
                    for (Hatch l : lukes) l.dispose();
                    for (Car c : cars) c.dispose();
                    for (Shawarma s : shawarma) s.dispose();
                    Resurces.lukest = Resurces.loadTexture(Resurces.PATH_LUKE_DESERT);
                    Resurces.carTex = Resurces.loadTexture(Resurces.PATH_CAR_DESERT);
                    Resurces.shavermTex = Resurces.loadTexture(Resurces.PATH_SHAVERM_DESERT);

                    initLukes();
                    initCars();
                    initShaverm();
                    backdesert = true;
                }
                if (gamePoints > GameSettings.WINTER_UNLOCK_SCORE && !winter) {
                    background = new MovingBackground(Resurces.PATH_BG_WINTER);
                    for (Hatch l : lukes) l.dispose();
                    for (Car c : cars) c.dispose();
                    for (Shawarma s : shawarma) s.dispose();
                    Resurces.lukest = Resurces.loadTexture(Resurces.PATH_LUKE_WINTER);
                    Resurces.carTex = Resurces.loadTexture(Resurces.PATH_CAR_WINTER);
                    Resurces.shavermTex = Resurces.loadTexture(Resurces.PATH_SHAVERM_WINTER);

                    initLukes();
                    initCars();
                    initShaverm();
                    winter = true;
                }

                int touchY = Gdx.input.getY();

                if (buttonUp.isHint((int) touchPoint.x, (int) touchPoint.y)) {
                    person.onClickY();
                } else if (buttonDown.isHint((int) touchPoint.x, (int) touchPoint.y)) {
                    person.onClickYM();
                } else if (buttonJump.isHint((int) touchPoint.x, (int) touchPoint.y)) {
                    person.onClick();
                }
            } else if (gameState == GameState.PAUSED) {
                if (resumeButton.isHint((int) touchPoint.x, (int) touchPoint.y)) {
                    gameState = GameState.PLAYING;
                    if (!Resurces.gameMusic.isPlaying()) {
                        Resurces.gameMusic.play();
                    }
                } else if (restartButton.isHint((int) touchPoint.x, (int) touchPoint.y)) {
                    show();
                } else if (menuButton.isHint((int) touchPoint.x, (int) touchPoint.y)) {
                    Resurces.gameMusic.stop();
                    main.setScreen(main.screenMenu);
                } else if (pauseSoundBtn.isHint((int) touchPoint.x, (int) touchPoint.y)) {
                    main.screenSounds.fromGame = true;
                    main.setScreen(main.screenSounds);
                }
            }
        }
    }

    private void updateGame(float delta) {
        if (eat <= 0) {
            gameState = GameState.GAME_OVER;
            return;
        }

        person.move();

        boolean isPersonMoving = person.isMoving();

        if (isPersonMoving) {
            background.move();

            for (Car car : cars) {
                car.moveWithBackground(BACKGROUND_SPEED);
            }

            for (Hatch hatch1 : lukes) {
                hatch1.moveWithBackground(BACKGROUND_SPEED);
            }

            for (Shawarma shawarma1 : shawarma) {
                shawarma1.moveWithBackground(BACKGROUND_SPEED);
            }
        }

        for (Car car : cars) {
            if (car.isActive) {
                car.moveDown();
            }
        }

        carSpawnTimer += delta;
        if (carSpawnTimer >= CAR_SPAWN_INTERVAL) {
            trySpawnCar();
            carSpawnTimer = 0;
        }

        int steps = person.getStepsTaken();
        if (steps > gamePoints) {
            gamePoints = steps;
            eat -= GameSettings.EAT_DRAIN_PER_STEP;
            if (eat < 0) eat = 0;
        }

        updateLukes();
        updateShaverm();

        for (Car car : cars) {
            if (car.isActive && car.y < -car.height) {
                car.isActive = false;
            }
        }

        for (Car car : cars) {
            if (car.isCollision(person)) {
                gameState = GameState.GAME_OVER;
                return;
            }
        }
        for (Hatch hatch1 : lukes) {
            if (hatch1.isCollision(person)) {
                gameState = GameState.GAME_OVER;
                return;
            }
        }
        for (Shawarma shawarma1 : shawarma) {
            if (shawarma1.isCollision(person)) {
                shawarma1.isActive = false;
                eat += GameSettings.EAT_GAIN_PER_SHAWARMA;
                if (eat > GameSettings.MAX_EAT) {
                    eat = GameSettings.MAX_EAT;
                }
            }
        }
    }

    private void handleGameOver() {
        int bestScore = preferences.getInteger(GameSettings.BEST_SCORE_KEY, 0);
        if (gamePoints > bestScore) {
            preferences.putInteger(GameSettings.BEST_SCORE_KEY, gamePoints);
            preferences.flush();
        }

        Resurces.loseSound.play();
        main.screenRestart.gamePoints = gamePoints;
        main.scoreManager.addScore(gamePoints);
        main.scoreManager.saveScores();
        main.setScreen(main.screenRestart);
    }

    private void renderGame() {
        main.camera.position.set(SCR_WIDTH / 2, SCR_HEIGHT / 2, 0);
        main.camera.update();

        main.batch.setProjectionMatrix(main.camera.combined);

        ScreenUtils.clear(GameSettings.GAME_CLEAR_R, GameSettings.GAME_CLEAR_G, GameSettings.GAME_CLEAR_B, 1f);
        main.batch.begin();

        background.onDraw(main.batch);

        drawableItems.clear();

        drawableItems.add(new DrawableItem(person, person.y, 1));

        for (Hatch hatch1 : lukes) {
            if (hatch1.isActive) {
                drawableItems.add(new DrawableItem(hatch1, hatch1.y, 2));
            }
        }

        for (Shawarma shawarma1 : shawarma) {
            if (shawarma1.isActive) {
                drawableItems.add(new DrawableItem(shawarma1, shawarma1.y, 3));
            }
        }

        for (Car car : cars) {
            if (car.isActive) {
                drawableItems.add(new DrawableItem(car, car.y + 1000, 4));
            }
        }

        drawableItems.sort((o1, o2) -> {
            if (o1.layer != o2.layer) {
                return Integer.compare(o1.layer, o2.layer);
            }
            return Float.compare(o1.drawY, o2.drawY);
        });

        for (DrawableItem item : drawableItems) {
            item.draw(main.batch);
        }

        pointCounter.draw(main.batch, gamePoints);
        eatCounter.draw(main.batch, eat);

        if (gameState == GameState.PLAYING) {
            pauseButton.draw(main.batch);
        }

        if (gameState == GameState.PAUSED) {
            main.batch.setColor(0, 0, 0, 0.7f);
            main.batch.draw(Resurces.whitePixel, 0, 0, SCR_WIDTH, SCR_HEIGHT);
            main.batch.setColor(1, 1, 1, 1);

            String pauseText = "PAUSED";
            pauseFont.draw(main.batch, pauseText, SCR_WIDTH / 2 - 60, SCR_HEIGHT - 100);

            resumeButton.draw(main.batch);
            restartButton.draw(main.batch);
            menuButton.draw(main.batch);
            pauseSoundBtn.draw(main.batch);
        }
        if (gameState == GameState.PLAYING) {
            buttonUp.draw(main.batch);
            buttonDown.draw(main.batch);
            buttonJump.draw(main.batch);
        }

        main.batch.end();
    }

    private void trySpawnCar() {
        int activeCars = 0;
        for (Car car : cars) {
            if (car.isActive) {
                activeCars++;
            }
        }

        if (activeCars >= GameSettings.MAX_ACTIVE_CARS) {
            return;
        }

        Car carToSpawn = null;
        for (Car car : cars) {
            if (!car.isActive) {
                carToSpawn = car;
                break;
            }
        }

        if (carToSpawn == null) {
            return;
        }

        float spawnX = (float) (Math.random() * (SCR_WIDTH - carToSpawn.width * 3)) + carToSpawn.width * 1.5f;

        boolean canSpawn = true;
        for (Car car : cars) {
            if (car.isActive && car != carToSpawn) {
                float xDistance = Math.abs(car.x - spawnX);
                if (xDistance < carToSpawn.width * 1.5f) {
                    float yDistance = Math.abs(car.y - SCR_HEIGHT);
                    if (yDistance < MIN_CAR_DISTANCE) {
                        canSpawn = false;
                        break;
                    }
                }
            }
        }

        if (canSpawn) {
            carToSpawn.isActive = true;
            carToSpawn.y = SCR_HEIGHT;
            carToSpawn.x = spawnX;

            try {
                carToSpawn.getClass().getMethod("resetConstantly", float.class).invoke(carToSpawn, SCR_HEIGHT);
            } catch (Exception ignored) {
            }
        }
    }

    private void initCars() {
        cars = new Car[GameSettings.CAR_COUNT];
        for (int i = 0; i < GameSettings.CAR_COUNT; i++) {
            cars[i] = new Car(GameSettings.CAR_COUNT, i);
            cars[i].isActive = false;
        }

        for (int i = 0; i < GameSettings.INITIAL_CAR_SPAWN_COUNT; i++) {
            trySpawnCar();
        }
    }

    private void updateLukes() {
        float maxX = SCR_WIDTH;
        int activeLukes = 0;

        for (Hatch hatch1 : lukes) {
            if (hatch1.isActive) {
                if (hatch1.x > maxX) {
                    maxX = hatch1.x;
                }
                activeLukes++;
            }
        }

        int minActiveLukes = GameSettings.MIN_ACTIVE_LUKES;
        for (Hatch hatch1 : lukes) {
            if (!hatch1.isActive && activeLukes < minActiveLukes) {
                hatch1.reset(maxX);
                activeLukes++;
            }
        }
    }

    private void initLukes() {
        lukes = new Hatch[GameSettings.LUKE_COUNT];
        for (int i = 0; i < GameSettings.LUKE_COUNT; i++) {
            lukes[i] = new Hatch(GameSettings.LUKE_COUNT, i);
        }
    }

    private void updateShaverm() {
        float maxX = SCR_WIDTH;
        int activeShaverm = 0;

        for (Shawarma shawarma1 : shawarma) {
            if (shawarma1.isActive) {
                if (shawarma1.x > maxX) {
                    maxX = shawarma1.x;
                }
                activeShaverm++;
            }
        }

        int minActiveShaverm = GameSettings.MIN_ACTIVE_SHAVERM;
        for (Shawarma shawarma1 : shawarma) {
            if (!shawarma1.isActive && activeShaverm < minActiveShaverm) {
                shawarma1.reset(maxX);
                activeShaverm++;
            }
        }
    }

    private void initShaverm() {
        shawarma = new Shawarma[GameSettings.SHAVERM_COUNT];
        for (int i = 0; i < GameSettings.SHAVERM_COUNT; i++) {
            shawarma[i] = new Shawarma(GameSettings.SHAVERM_COUNT, i);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        if (gameState == GameState.PLAYING) {
            gameState = GameState.PAUSED;
            Resurces.gameMusic.pause();
        }
    }

    @Override
    public void resume() {
        if (gameState == GameState.PAUSED) {
            gameState = GameState.PLAYING;
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
        person.dispose();
        for (Car car : cars) {
            car.dispose();
        }
        for (Hatch hatch1 : lukes) {
            hatch1.dispose();
        }
        for (Shawarma shawarma1 : shawarma) {
            shawarma1.dispose();
        }
        pointCounter.dispose();
        eatCounter.dispose();
        background.dispose();
        pauseFont.dispose();
        pauseButton.dispose();
        resumeButton.dispose();
        restartButton.dispose();
        menuButton.dispose();
        pauseSoundBtn.dispose();
        buttonUp.dispose();
        buttonDown.dispose();
        buttonJump.dispose();
    }

    private class DrawableItem {
        private GameObject object;
        private float drawY;
        private int layer;

        DrawableItem(GameObject object, float drawY, int layer) {
            this.object = object;
            this.drawY = drawY;
            this.layer = layer;
        }

        void draw(com.badlogic.gdx.graphics.g2d.Batch batch) {
            object.draw(batch);
        }
    }
}

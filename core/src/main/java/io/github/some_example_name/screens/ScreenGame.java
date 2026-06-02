package io.github.some_example_name.screens;

import static io.github.some_example_name.Main.SCR_HEIGHT;
import static io.github.some_example_name.Main.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Array;

import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.characters.Lukes;
import io.github.some_example_name.characters.Person;
import io.github.some_example_name.characters.Car;
import io.github.some_example_name.characters.Shaverm;
import io.github.some_example_name.components.EatCounter;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.PointCounter;
import io.github.some_example_name.components.TextButton;
import io.github.some_example_name.components.TextButtonWalk;

public class ScreenGame implements Screen {
    Main main;
    Person person;
    MovingBackground background;
    Lukes[] lukes;
    Car[] cars;
    Shaverm[] shaverm;
    boolean isGameOver;
    int gamePoints;
    PointCounter pointCounter;
    EatCounter eatCounter;
    final int pointCounterMarginRight = 300;
    final int pointCounterMarginTop = 60;
    final int CAR_COUNT = 20;
    final int LUKE_COUNT = 5;
    final int SHAVERM_COUNT = 5;
    private TextButtonWalk buttonUp;
    private TextButtonWalk buttonDown;
    private TextButtonWalk buttonJump;
    private boolean backdesert = false;

    int eat = 100;

    private static Preferences preferences = Gdx.app.getPreferences("User saves");
    private final int BACKGROUND_SPEED = 3;

    private Array<DrawableItem> drawableItems = new Array<>();

    private float carSpawnTimer = 0;
    private final float CAR_SPAWN_INTERVAL = 0.7f;
    private final float MIN_CAR_DISTANCE = 200;

    private enum GameState {
        PLAYING, PAUSED, GAME_OVER
    }

    private GameState gameState = GameState.PLAYING;

    private TextButton pauseButton;
    private TextButton resumeButton;
    private TextButton restartButton;
    private TextButton menuButton;

    private Vector3 touchPoint = new Vector3();

    private BitmapFont pauseFont;

    private final int PAUSE_BUTTON_SPACING = 60;
    private final int BUTTON_HEIGHT = 50;
    private final int BUTTON_OFFSET_X = 200;
    private final int BUTTON_OFFSET_Y = -80;

    private final int SAFE_SPAWN_X = SCR_WIDTH / 2 - 50;
    private final int SAFE_SPAWN_Y = 100;

    private final float TOP_ZONE_PERCENT = 0.35f;
    private final float BOTTOM_ZONE_PERCENT = 0.35f;
    private final int TOP_ZONE_HEIGHT = (int) (SCR_HEIGHT * TOP_ZONE_PERCENT);
    private final int BOTTOM_ZONE_HEIGHT = (int) (SCR_HEIGHT * BOTTOM_ZONE_PERCENT);
    private final int BUTTON_SIZE = 100;
    private final int BUTTON_MARGIN = 30;
    private final int BUTTONS_POS_X = 80;
    private final int BUTTON_UP_Y = SCR_HEIGHT - BUTTON_SIZE - BUTTON_MARGIN;
    private final int BUTTON_DOWN_Y = BUTTON_MARGIN;
    public Texture lukest = new Texture("images/luk.png");
    public Texture carTex = new Texture("cars/car0.png");
    public Texture shavermTex = new Texture("images/Shaverma.png");

    public ScreenGame(Main main) {
        this.main = main;

        initCars();
        initLukes();
        initShaverm();

        person = new Person(findSafeSpawnPosition(), 100, 20, 100, 200);
        person.updateTextures();

        pointCounter = new PointCounter(SCR_WIDTH - pointCounterMarginRight - 100, SCR_HEIGHT - pointCounterMarginTop);
        eatCounter = new EatCounter(SCR_WIDTH - pointCounterMarginRight - 900, SCR_HEIGHT - pointCounterMarginTop);
        background = new MovingBackground("backgrounds/road_bg.png");

        pauseButton = createCompactButton(SCR_WIDTH - 120, SCR_HEIGHT - 80, "II", 2.5f, 60, 60);

        setupPauseMenuButtons();
        initMovementButtons();
        pauseFont = new BitmapFont();
        pauseFont.getData().setScale(2.0f);
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
        buttonJump = new TextButtonWalk(1100, 100, "", "images/right.png");
        buttonJump.buttonWidth = BUTTON_SIZE;
        buttonJump.buttonHeight = BUTTON_SIZE;
        buttonJump.textX = buttonJump.x + (BUTTON_SIZE - buttonJump.textWidth) / 2;
        buttonJump.textY = buttonJump.y + BUTTON_SIZE / 2 + buttonJump.textHeight / 2;
        buttonJump.font.getData().setScale(2.0f);

        buttonUp = new TextButtonWalk(1000, 150, "","images/up.png");
        buttonUp.buttonWidth = BUTTON_SIZE;
        buttonUp.buttonHeight = BUTTON_SIZE;
        buttonUp.textX = buttonUp.x + (BUTTON_SIZE - buttonUp.textWidth) / 2;
        buttonUp.textY = buttonUp.y + BUTTON_SIZE / 2 + buttonUp.textHeight / 2;
        buttonUp.font.getData().setScale(2.0f);

        buttonDown = new TextButtonWalk(1000, 50, "","images/down.png" );
        buttonDown.buttonWidth = BUTTON_SIZE;
        buttonDown.buttonHeight = BUTTON_SIZE;
        buttonDown.textX = buttonDown.x + (BUTTON_SIZE - buttonDown.textWidth) / 2;
        buttonDown.textY = buttonDown.y + BUTTON_SIZE / 2 + buttonDown.textHeight / 2;
        buttonDown.font.getData().setScale(2.0f);
    }
    private int findSafeSpawnPosition() {
        int safeX = SAFE_SPAWN_X;
        int attempts = 0;
        final int safeAreaWidth = 200;

        boolean safePositionFound = false;

        while (!safePositionFound && attempts < 10) {
            safePositionFound = true;

            for (Lukes lukes1 : lukes) {
                if (lukes1 != null && lukes1.isActive) {
                    boolean xOverlap = Math.abs(lukes1.x - safeX) < (lukes1.width + safeAreaWidth) / 2;
                    boolean yOverlap = Math.abs(lukes1.y - SAFE_SPAWN_Y) < (lukes1.height + 200) / 2;

                    if (xOverlap && yOverlap) {
                        safePositionFound = false;
                        safeX = (safeX + 150) % (SCR_WIDTH - 200);
                        break;
                    }
                }
            }

            for (Shaverm shaverm1 : shaverm) {
                if (shaverm1 != null && shaverm1.isActive) {
                    boolean xOverlap = Math.abs(shaverm1.x - safeX) < (shaverm1.width + safeAreaWidth) / 2;
                    boolean yOverlap = Math.abs(shaverm1.y - SAFE_SPAWN_Y) < (shaverm1.height + 200) / 2;

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
        int baseY = centerY + BUTTON_OFFSET_Y;

        int resumeY = baseY + BUTTON_HEIGHT + PAUSE_BUTTON_SPACING;
        int restartY = baseY;
        int menuY = baseY - BUTTON_HEIGHT - PAUSE_BUTTON_SPACING;

        resumeButton = new TextButton(buttonX, resumeY, "RESUME");
        restartButton = new TextButton(buttonX, restartY, "RESTART");
        menuButton = new TextButton(buttonX, menuY, "MENU");
    }

    @Override
    public void show() {
        background = new MovingBackground("backgrounds/road_bg.png");
        if (lukest != null) lukest.dispose();
        if (carTex != null) carTex.dispose();
        if (shavermTex != null) shavermTex.dispose();
        lukest = new Texture("images/luk.png");
        carTex = new Texture("cars/car0.png");
        shavermTex = new Texture("images/Shaverma.png");
        isGameOver = false;
        backdesert = false;
        gamePoints = 0;
        eat = 100;
        gameState = GameState.PLAYING;

        for (Car car : cars) {
            car.dispose();
        }
        for (Lukes lukes1 : lukes) {
            lukes1.dispose();
        }
        for (Shaverm shaverm1 : shaverm) {
            shaverm1.dispose();
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
        for (Lukes lukes1 : lukes) {
            if (lukes1.isActive && lukes1.isCollision(person)) {
                lukes1.isActive = false;
            }
        }

        for (Shaverm shaverm1 : shaverm) {
            if (shaverm1.isActive && shaverm1.isCollision(person)) {
                shaverm1.isActive = false;
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
                if (gamePoints>200 && backdesert==false){
                    background = new MovingBackground("backgrounds/desert.jpg");
                    for (Lukes l : lukes) l.dispose();
                    for (Car c : cars) c.dispose();
                    for (Shaverm s : shaverm) s.dispose();
                    if (lukest != null) lukest.dispose();
                    if (carTex != null) carTex.dispose();
                    if (shavermTex != null) shavermTex.dispose();
                    lukest = new Texture("images/Kol.png");
                    carTex = new Texture("images/gruz.png");
                    shavermTex = new Texture("images/plod.png");

                    initLukes();
                    initCars();
                    initShaverm();
                    backdesert = true;
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

            for (Lukes lukes1 : lukes) {
                lukes1.moveWithBackground(BACKGROUND_SPEED);
            }

            for (Shaverm shaverm1 : shaverm) {
                shaverm1.moveWithBackground(BACKGROUND_SPEED);
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
            eat -= 2;
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
        for (Lukes lukes1 : lukes) {
            if (lukes1.isCollision(person)) {
                gameState = GameState.GAME_OVER;
                return;
            }
        }
        for (Shaverm shaverm1 : shaverm) {
            if (shaverm1.isCollision(person)) {
                shaverm1.isActive = false;
                eat += 20;
                if (eat > 100) {
                    eat = 100;
                }
            }
        }
    }

    private void handleGameOver() {
        int bestScore = preferences.getInteger("best_score", 0);
        if (gamePoints > bestScore) {
            preferences.putInteger("best_score", gamePoints);
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

        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1f);
        main.batch.begin();

        background.onDraw(main.batch);


        drawableItems.clear();


        drawableItems.add(new DrawableItem(person, person.y, 1));

        for (Lukes lukes1 : lukes) {
            if (lukes1.isActive) {
                drawableItems.add(new DrawableItem(lukes1, lukes1.y, 2));
            }
        }

        for (Shaverm shaverm1 : shaverm) {
            if (shaverm1.isActive) {
                drawableItems.add(new DrawableItem(shaverm1, shaverm1.y, 3));
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
            main.batch.draw(Resurces.getPixelTexture(), 0, 0, SCR_WIDTH, SCR_HEIGHT);
            main.batch.setColor(1, 1, 1, 1);

            String pauseText = "PAUSED";
            pauseFont.draw(main.batch, pauseText, SCR_WIDTH / 2 - 60, SCR_HEIGHT - 100);

            resumeButton.draw(main.batch);
            restartButton.draw(main.batch);
            menuButton.draw(main.batch);
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

        if (activeCars >= 8) {
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
        cars = new Car[CAR_COUNT];
        for (int i = 0; i < CAR_COUNT; i++) {
            cars[i] = new Car(CAR_COUNT, i);
            cars[i].isActive = false;
            cars[i].screenGame = this;
        }

        for (int i = 0; i < 5; i++) {
            trySpawnCar();
        }
    }

    private void updateLukes() {
        float maxX = SCR_WIDTH;
        int activeLukes = 0;

        for (Lukes lukes1 : lukes) {
            if (lukes1.isActive) {
                if (lukes1.x > maxX) {
                    maxX = lukes1.x;
                }
                activeLukes++;
            }
        }

        int minActiveLukes = 5;
        for (Lukes lukes1 : lukes) {
            if (!lukes1.isActive && activeLukes < minActiveLukes) {
                lukes1.reset(maxX);
                activeLukes++;
            }
        }
    }

    private void initLukes() {
        lukes = new Lukes[LUKE_COUNT];
        for (int i = 0; i < LUKE_COUNT; i++) {
            lukes[i] = new Lukes(LUKE_COUNT, i);
            lukes[i].screenGame = this;
        }
    }

    private void updateShaverm() {
        float maxX = SCR_WIDTH;
        int activeShaverm = 0;

        for (Shaverm shaverm1 : shaverm) {
            if (shaverm1.isActive) {
                if (shaverm1.x > maxX) {
                    maxX = shaverm1.x;
                }
                activeShaverm++;
            }
        }

        int minActiveShaverm = 4;
        for (Shaverm shaverm1 : shaverm) {
            if (!shaverm1.isActive && activeShaverm < minActiveShaverm) {
                shaverm1.reset(maxX);
                activeShaverm++;
            }
        }
    }

    private void initShaverm() {
        shaverm = new Shaverm[SHAVERM_COUNT];
        for (int i = 0; i < SHAVERM_COUNT; i++) {
            shaverm[i] = new Shaverm(SHAVERM_COUNT, i);
            shaverm[i].screenGame = this;
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
        for (Lukes lukes1 : lukes) {
            lukes1.dispose();
        }
        for (Shaverm shaverm1 : shaverm) {
            shaverm1.dispose();
        }
        pointCounter.dispose();
        eatCounter.dispose();
        background.dispose();
        if (lukest != null) lukest.dispose();
        if (carTex != null) carTex.dispose();
        if (shavermTex != null) shavermTex.dispose();
        pauseFont.dispose();
        pauseButton.dispose();
        resumeButton.dispose();
        restartButton.dispose();
        menuButton.dispose();
        buttonUp.dispose();
        buttonDown.dispose();
        buttonJump.dispose();
    }

    private class DrawableItem {
        private Object object;
        private float drawY;
        private int layer;

        DrawableItem(Object object, float drawY, int layer) {
            this.object = object;
            this.drawY = drawY;
            this.layer = layer;
        }

        void draw(com.badlogic.gdx.graphics.g2d.Batch batch) {
            if (object instanceof Car) {
                ((Car) object).draw(batch);
            } else if (object instanceof Lukes) {
                ((Lukes) object).draw(batch);
            } else if (object instanceof Shaverm) {
                ((Shaverm) object).draw(batch);
            } else if (object instanceof Person) {
                ((Person) object).draw(batch);
            }
        }
    }
}

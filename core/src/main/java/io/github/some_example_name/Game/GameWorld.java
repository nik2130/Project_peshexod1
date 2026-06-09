package io.github.some_example_name.Game;

import static io.github.some_example_name.Config.ScreenConfig.SCR_HEIGHT;
import static io.github.some_example_name.Config.ScreenConfig.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import io.github.some_example_name.Config.GameplayConfig;
import io.github.some_example_name.Config.SaveConfig;
import io.github.some_example_name.Config.SpawnConfig;

import io.github.some_example_name.Main;
import io.github.some_example_name.Resurces;
import io.github.some_example_name.characters.Car;
import io.github.some_example_name.characters.Hatch;
import io.github.some_example_name.characters.Person;
import io.github.some_example_name.characters.Shawarma;
import io.github.some_example_name.components.MovingBackground;

public class GameWorld {
    public Person person;
    public Hatch[] lukes;
    public Car[] cars;
    public Shawarma[] shawarma;
    public int gamePoints;
    public int eat = GameplayConfig.INITIAL_EAT;
    public GameState gameState = GameState.PLAYING;
    public Biome biome = Biome.ROAD;
    public MovingBackground background;
    public float carSpawnTimer;

    private static final Preferences preferences = Gdx.app.getPreferences(SaveConfig.USER_SAVES);
    private GameSpawner spawner;

    public void init(Main main) {
        background = new MovingBackground(Resurces.PATH_BG_ROAD);

        Resurces.lukest = Resurces.loadTexture(Resurces.PATH_LUKE);
        Resurces.carTex = Resurces.loadTexture(Resurces.PATH_CAR);
        Resurces.shavermTex = Resurces.loadTexture(Resurces.PATH_SHAVERM);

        gamePoints = 0;
        eat = GameplayConfig.INITIAL_EAT;
        gameState = GameState.PLAYING;
        biome = Biome.ROAD;
        carSpawnTimer = 0;

        if (spawner == null) {
            spawner = new GameSpawner();
        }

        disposeEntities();
        spawner.init(this);

        if (person != null) {
            person.dispose();
        }

        int safeSpawnX = spawner.findSafeSpawnPosition(this);
        person = new Person(safeSpawnX, SpawnConfig.SAFE_SPAWN_Y, 50, 100);
        person.updateTextures();

        spawner.checkSpawnSafety(this);

        Resurces.gameMusic.play();
    }

    public void update(float delta) {
        if (eat <= 0) {
            gameState = GameState.GAME_OVER;
            return;
        }

        person.move();

        boolean isPersonMoving = person.isMoving();

        if (isPersonMoving) {
            background.move();

            int speed = GameplayConfig.BACKGROUND_SPEED;
            for (Car car : cars) {
                car.moveWithBackground(speed);
            }
            for (Hatch hatch1 : lukes) {
                hatch1.moveWithBackground(speed);
            }
            for (Shawarma shawarma1 : shawarma) {
                shawarma1.moveWithBackground(speed);
            }
        }

        for (Car car : cars) {
            if (car.isActive) {
                car.moveDown();
            }
        }

        carSpawnTimer += delta;
        if (carSpawnTimer >= SpawnConfig.CAR_SPAWN_INTERVAL) {
            spawner.trySpawnCar(this);
            carSpawnTimer = 0;
        }

        int steps = person.getStepsTaken();
        if (steps > gamePoints) {
            gamePoints = steps;
            eat -= GameplayConfig.EAT_DRAIN_PER_STEP;
            if (eat < 0) eat = 0;
        }

        spawner.updateLukes(this);
        spawner.updateShaverm(this);

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
                eat += GameplayConfig.EAT_GAIN_PER_SHAWARMA;
                if (eat > GameplayConfig.MAX_EAT) {
                    eat = GameplayConfig.MAX_EAT;
                }
            }
        }
    }

    public void handleGameOver(Main main) {
        int bestScore = preferences.getInteger(SaveConfig.BEST_SCORE_KEY, 0);
        if (gamePoints > bestScore) {
            preferences.putInteger(SaveConfig.BEST_SCORE_KEY, gamePoints);
            preferences.flush();
        }

        Resurces.loseSound.play();
        main.screenRestart.gamePoints = gamePoints;
        main.scoreManager.addScore(gamePoints);
        main.scoreManager.saveScores();
        main.setScreen(main.screenRestart);
    }

    public void switchBiomeDesert() {
        background = new MovingBackground(Resurces.PATH_BG_DESERT);
        for (Hatch l : lukes) l.dispose();
        for (Car c : cars) c.dispose();
        for (Shawarma s : shawarma) s.dispose();
        Resurces.lukest = Resurces.loadTexture(Resurces.PATH_LUKE_DESERT);
        Resurces.carTex = Resurces.loadTexture(Resurces.PATH_CAR_DESERT);
        Resurces.shavermTex = Resurces.loadTexture(Resurces.PATH_SHAVERM_DESERT);

        spawner.init(this);
        biome = Biome.DESERT;
    }

    public void switchBiomeWinter() {
        background = new MovingBackground(Resurces.PATH_BG_WINTER);
        for (Hatch l : lukes) l.dispose();
        for (Car c : cars) c.dispose();
        for (Shawarma s : shawarma) s.dispose();
        Resurces.lukest = Resurces.loadTexture(Resurces.PATH_LUKE_WINTER);
        Resurces.carTex = Resurces.loadTexture(Resurces.PATH_CAR_WINTER);
        Resurces.shavermTex = Resurces.loadTexture(Resurces.PATH_SHAVERM_WINTER);

        spawner.init(this);
        biome = Biome.WINTER;
    }

    public boolean isDesertUnlocked() {
        return gamePoints > GameplayConfig.DESERT_UNLOCK_SCORE && biome != Biome.DESERT;
    }

    public boolean isWinterUnlocked() {
        return gamePoints > GameplayConfig.WINTER_UNLOCK_SCORE && biome != Biome.WINTER;
    }

    public void dispose() {
        disposeEntities();
        if (person != null) person.dispose();
        if (background != null) background.dispose();
    }

    private void disposeEntities() {
        if (cars != null) {
            for (Car car : cars) car.dispose();
        }
        if (lukes != null) {
            for (Hatch hatch1 : lukes) hatch1.dispose();
        }
        if (shawarma != null) {
            for (Shawarma shawarma1 : shawarma) shawarma1.dispose();
        }
    }
}

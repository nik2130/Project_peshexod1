package io.github.some_example_name.Game;

import static io.github.some_example_name.Config.ScreenConfig.SCR_HEIGHT;
import static io.github.some_example_name.Config.ScreenConfig.SCR_WIDTH;

import io.github.some_example_name.Config.SpawnConfig;
import io.github.some_example_name.characters.Car;
import io.github.some_example_name.characters.Hatch;
import io.github.some_example_name.characters.Person;
import io.github.some_example_name.characters.Shawarma;

public class GameSpawner {
    public void init(GameWorld world) {
        initCars(world);
        initLukes(world);
        initShaverm(world);
    }

    public void initCars(GameWorld world) {
        world.cars = new Car[SpawnConfig.CAR_COUNT];
        for (int i = 0; i < SpawnConfig.CAR_COUNT; i++) {
            world.cars[i] = new Car(SpawnConfig.CAR_COUNT, i);
            world.cars[i].isActive = false;
        }
        for (int i = 0; i < SpawnConfig.INITIAL_CAR_SPAWN_COUNT; i++) {
            trySpawnCar(world);
        }
    }

    public void initLukes(GameWorld world) {
        world.lukes = new Hatch[SpawnConfig.LUKE_COUNT];
        for (int i = 0; i < SpawnConfig.LUKE_COUNT; i++) {
            world.lukes[i] = new Hatch(SpawnConfig.LUKE_COUNT, i);
        }
    }

    public void initShaverm(GameWorld world) {
        world.shawarma = new Shawarma[SpawnConfig.SHAVERM_COUNT];
        for (int i = 0; i < SpawnConfig.SHAVERM_COUNT; i++) {
            world.shawarma[i] = new Shawarma();
        }
    }

    public void updateLukes(GameWorld world) {
        float maxX = SCR_WIDTH;
        int activeLukes = 0;

        for (Hatch hatch1 : world.lukes) {
            if (hatch1.isActive) {
                if (hatch1.x > maxX) {
                    maxX = hatch1.x;
                }
                activeLukes++;
            }
        }

        int minActiveLukes = SpawnConfig.MIN_ACTIVE_LUKES;
        for (Hatch hatch1 : world.lukes) {
            if (!hatch1.isActive && activeLukes < minActiveLukes) {
                hatch1.reset(maxX);
                activeLukes++;
            }
        }
    }

    public void updateShaverm(GameWorld world) {
        float maxX = SCR_WIDTH;
        int activeShaverm = 0;

        for (Shawarma shawarma1 : world.shawarma) {
            if (shawarma1.isActive) {
                if (shawarma1.x > maxX) {
                    maxX = shawarma1.x;
                }
                activeShaverm++;
            }
        }

        int minActiveShaverm = SpawnConfig.MIN_ACTIVE_SHAVERM;
        for (Shawarma shawarma1 : world.shawarma) {
            if (!shawarma1.isActive && activeShaverm < minActiveShaverm) {
                shawarma1.reset(maxX);
                activeShaverm++;
            }
        }
    }

    public void trySpawnCar(GameWorld world) {
        int activeCars = 0;
        for (Car car : world.cars) {
            if (car.isActive) {
                activeCars++;
            }
        }

        if (activeCars >= SpawnConfig.MAX_ACTIVE_CARS) {
            return;
        }

        Car carToSpawn = null;
        for (Car car : world.cars) {
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
        for (Car car : world.cars) {
            if (car.isActive && car != carToSpawn) {
                float xDistance = Math.abs(car.x - spawnX);
                if (xDistance < carToSpawn.width * 1.5f) {
                    float yDistance = Math.abs(car.y - SCR_HEIGHT);
                    if (yDistance < SpawnConfig.MIN_CAR_DISTANCE) {
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

    public int findSafeSpawnPosition(GameWorld world) {
        int safeX = SpawnConfig.SAFE_SPAWN_X;
        int attempts = 0;
        final int safeAreaWidth = SpawnConfig.SAFE_AREA_WIDTH;

        boolean safePositionFound = false;

        while (!safePositionFound && attempts < SpawnConfig.SAFE_SPAWN_ATTEMPTS) {
            safePositionFound = true;

            for (Hatch hatch1 : world.lukes) {
                if (hatch1 != null && hatch1.isActive) {
                    boolean xOverlap = Math.abs(hatch1.x - safeX) < (float) (hatch1.width + safeAreaWidth) / 2;
                    boolean yOverlap = Math.abs(hatch1.y - SpawnConfig.SAFE_SPAWN_Y) < (float) (hatch1.height + 200) / 2;

                    if (xOverlap && yOverlap) {
                        safePositionFound = false;
                        safeX = (safeX + 150) % (SCR_WIDTH - 200);
                        break;
                    }
                }
            }

            for (Shawarma shawarma1 : world.shawarma) {
                if (shawarma1 != null && shawarma1.isActive) {
                    boolean xOverlap = Math.abs(shawarma1.x - safeX) < (float) (shawarma1.width + safeAreaWidth) / 2;
                    boolean yOverlap = Math.abs(shawarma1.y - SpawnConfig.SAFE_SPAWN_Y) < (float) (shawarma1.height + 200) / 2;

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

    public void checkSpawnSafety(GameWorld world) {
        Person person = world.person;
        if (person == null) return;

        for (Hatch hatch1 : world.lukes) {
            if (hatch1.isActive && hatch1.isCollision(person)) {
                hatch1.isActive = false;
            }
        }

        for (Shawarma shawarma1 : world.shawarma) {
            if (shawarma1.isActive && shawarma1.isCollision(person)) {
                shawarma1.isActive = false;
            }
        }
    }
}

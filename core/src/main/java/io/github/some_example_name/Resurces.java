package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Resurces {

    // --- paths ---
    public static final String PATH_BUTTON_BG = "backgrounds/button_bg.png";
    public static final String PATH_BG_RESTART = "backgrounds/restart_bg.png";
    public static final String PATH_BG_ROAD = "backgrounds/road_bg.png";
    public static final String PATH_BG_DESERT = "backgrounds/desert.jpg";
    public static final String PATH_BG_WINTER = "backgrounds/fonz.jpg";
    public static final String PATH_LOGO = "images/logo.png";
    public static final String PATH_RIGHT = "images/right.png";
    public static final String PATH_UP = "images/up.png";
    public static final String PATH_DOWN = "images/down.png";
    public static final String PATH_LUKE = "images/luk.png";
    public static final String PATH_CAR = "cars/car0.png";
    public static final String PATH_SHAVERM = "images/Shaverma.png";
    public static final String PATH_LUKE_DESERT = "images/Kol.png";
    public static final String PATH_CAR_DESERT = "images/gruz.png";
    public static final String PATH_SHAVERM_DESERT = "images/plod.png";
    public static final String PATH_LUKE_WINTER = "images/snegov.png";
    public static final String PATH_CAR_WINTER = "images/snegox.png";
    public static final String PATH_SHAVERM_WINTER = "images/cand.png";
    public static final String PATH_ACH_1000 = "images/achievement_1000.png";
    public static final String PATH_ACH_CACTUS = "images/achievement_cactus.png";
    public static final String PATH_ACH_SNOWMAN = "images/achievement_snowman.png";

    public static final String PATH_AUDIO_MENU = "audio/music/menu.mp3";
    public static final String PATH_AUDIO_GAME = "audio/music/game2.mp3";
    public static final String PATH_AUDIO_LOSE = "audio/sounds/lose.mp3";

    // --- textures ---
    public static Texture lukest;
    public static Texture carTex;
    public static Texture shavermTex;
    public static Texture ach1000Badge;
    public static Texture achCactusBadge;
    public static Texture achSnowmanBadge;
    public static Texture skinTexture;
    public static Texture whitePixel;

    // --- skin paths ---
    public static String skin = "skins/bluebird/blue_birdS.png";
    public static String PERSON0_IMG_PATH = "skins/bluebird/blue_bird0.png";
    public static String PERSON1_IMG_PATH = "skins/bluebird/blue_bird1.png";
    public static String PERSON2_IMG_PATH = "skins/bluebird/blue_bird2.png";
    public static String PERSON3_IMG_PATH = "skins/bluebird/blue_bird0.png";
    public static String PERSON4_IMG_PATH = "skins/bluebird/blue_bird1.png";
    public static String PERSON5_IMG_PATH = "skins/bluebird/blue_bird2.png";
    public static String PERSON6_IMG_PATH = "skins/bluebird/blue_bird0.png";
    public static String PERSON7_IMG_PATH = "skins/bluebird/blue_bird1.png";

    public static float Volume = 0.5f;

    public static Music menuMusic;
    public static Music gameMusic;
    public static Music loseSound;

    private static List<Music> allMusic = new ArrayList<>();

    public static void initMusic() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal(PATH_AUDIO_MENU));
        menuMusic.setLooping(true);
        menuMusic.setVolume(Volume);

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal(PATH_AUDIO_GAME));
        gameMusic.setLooping(true);
        gameMusic.setVolume(Volume);

        loseSound = Gdx.audio.newMusic(Gdx.files.internal(PATH_AUDIO_LOSE));
        loseSound.setVolume(Volume);

        allMusic.add(menuMusic);
        allMusic.add(gameMusic);
        allMusic.add(loseSound);
    }

    public static void initTextures() {
        lukest = new Texture(PATH_LUKE);
        carTex = new Texture(PATH_CAR);
        shavermTex = new Texture(PATH_SHAVERM);
        ach1000Badge = new Texture(PATH_ACH_1000);
        achCactusBadge = new Texture(PATH_ACH_CACTUS);
        achSnowmanBadge = new Texture(PATH_ACH_SNOWMAN);
        skinTexture = new Texture(skin);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whitePixel = new Texture(pixmap);
        pixmap.dispose();
    }

    public static void reloadSkinTexture() {
        if (skinTexture != null) skinTexture.dispose();
        skinTexture = new Texture(skin);
    }

    public static Texture loadTexture(String path) {
        return new Texture(path);
    }

    public static void updateVolume(float newVolume) {
        Volume = Math.max(0, Math.min(1, newVolume));

        for (Music music : allMusic) {
            music.setVolume(Volume);
        }
    }

    public static String getVolumePercent() {
        return Math.round(Volume * 100) + "%";
    }

    public static void disposeMusic() {
        for (Music music : allMusic) {
            music.dispose();
        }
        allMusic.clear();
        menuMusic = null;
        gameMusic = null;
    }

    public static void disposeTextures() {
        if (lukest != null) lukest.dispose();
        if (carTex != null) carTex.dispose();
        if (shavermTex != null) shavermTex.dispose();
        if (ach1000Badge != null) ach1000Badge.dispose();
        if (achCactusBadge != null) achCactusBadge.dispose();
        if (achSnowmanBadge != null) achSnowmanBadge.dispose();
        if (skinTexture != null) skinTexture.dispose();
        if (whitePixel != null) whitePixel.dispose();
    }


}

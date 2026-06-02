package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Resurces {
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
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/menu.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(Volume);

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/game2.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(Volume);

        loseSound = Gdx.audio.newMusic(Gdx.files.internal("audio/sounds/lose.mp3"));
        loseSound.setVolume(Volume);

        allMusic.add(menuMusic);
        allMusic.add(gameMusic);
        allMusic.add(loseSound);
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

    public static Texture getPixelTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        Texture pixelTexture = new Texture(pixmap);
        pixmap.dispose();
        return pixelTexture;
    }
}




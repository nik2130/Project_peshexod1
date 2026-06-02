package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreManager {
    private static final String PREFS_NAME = "game_prefs";
    private static final String SCORE_KEY = "total_score";
    private static final String HIGH_SCORE_KEY = "high_score";

    private int totalScore;
    private int highScore;

    public ScoreManager() {
        loadScores();
    }

    public void addScore(int points) {
        totalScore += points;

        if (points > highScore) {
            highScore = points;
        }

        saveScores();
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getHighScore() {
        return highScore;
    }

    private void loadScores() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        totalScore = prefs.getInteger(SCORE_KEY, 0);
        highScore = prefs.getInteger(HIGH_SCORE_KEY, 0);
    }

    public void saveScores() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        prefs.putInteger(SCORE_KEY, totalScore);
        prefs.putInteger(HIGH_SCORE_KEY, highScore);
        prefs.flush();
    }


    public void resetAllRecords() {
        totalScore = 0;
        highScore = 0;

        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        prefs.putInteger(SCORE_KEY, 0);
        prefs.putInteger(HIGH_SCORE_KEY, 0);
        prefs.flush();

    }
}

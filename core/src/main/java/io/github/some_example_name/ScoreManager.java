package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreManager {

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
        Preferences prefs = Gdx.app.getPreferences(GameSettings.PREFS_NAME);
        totalScore = prefs.getInteger(GameSettings.SCORE_KEY, 0);
        highScore = prefs.getInteger(GameSettings.HIGH_SCORE_KEY, 0);
    }

    public void saveScores() {
        Preferences prefs = Gdx.app.getPreferences(GameSettings.PREFS_NAME);
        prefs.putInteger(GameSettings.SCORE_KEY, totalScore);
        prefs.putInteger(GameSettings.HIGH_SCORE_KEY, highScore);
        prefs.flush();
    }

    public void resetAllRecords() {
        totalScore = 0;
        highScore = 0;

        Preferences prefs = Gdx.app.getPreferences(GameSettings.PREFS_NAME);
        prefs.putInteger(GameSettings.SCORE_KEY, 0);
        prefs.putInteger(GameSettings.HIGH_SCORE_KEY, 0);
        prefs.flush();
    }
}

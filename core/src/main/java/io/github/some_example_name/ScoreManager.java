package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import io.github.some_example_name.Config.SaveConfig;

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
        Preferences prefs = Gdx.app.getPreferences(SaveConfig.PREFS_NAME);
        totalScore = prefs.getInteger(SaveConfig.SCORE_KEY, 0);
        highScore = prefs.getInteger(SaveConfig.HIGH_SCORE_KEY, 0);
    }

    public void saveScores() {
        Preferences prefs = Gdx.app.getPreferences(SaveConfig.PREFS_NAME);
        prefs.putInteger(SaveConfig.SCORE_KEY, totalScore);
        prefs.putInteger(SaveConfig.HIGH_SCORE_KEY, highScore);
        prefs.flush();
    }

    public void resetAllRecords() {
        totalScore = 0;
        highScore = 0;

        Preferences prefs = Gdx.app.getPreferences(SaveConfig.PREFS_NAME);
        prefs.putInteger(SaveConfig.SCORE_KEY, 0);
        prefs.putInteger(SaveConfig.HIGH_SCORE_KEY, 0);
        prefs.flush();
    }
}

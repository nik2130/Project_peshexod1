package io.github.some_example_name;

public class GameSettings {
    // screen (defined in Main)
    public static final int SCR_WIDTH = Main.SCR_WIDTH;
    public static final int SCR_HEIGHT = Main.SCR_HEIGHT;

    // game counts
    public static final int CAR_COUNT = 20;
    public static final int LUKE_COUNT = 5;
    public static final int SHAVERM_COUNT = 5;

    // speeds
    public static final int BACKGROUND_SPEED = 3;
    public static final int BACKGROUND_SCROLL_SPEED = 5;
    public static final int CAR_DOWN_SPEED_MIN = 3;
    public static final int CAR_DOWN_SPEED_RANGE = 4;
    public static final int PERSON_MOVE_Y_SPEED = 5;

    // spawn
    public static final float CAR_SPAWN_INTERVAL = 0.7f;
    public static final float MIN_CAR_DISTANCE = 200;
    public static final int MAX_ACTIVE_CARS = 8;
    public static final int MIN_ACTIVE_LUKES = 5;
    public static final int MIN_ACTIVE_SHAVERM = 4;
    public static final int SPAWN_ATTEMPTS = 30;
    public static final int SAFE_SPAWN_ATTEMPTS = 10;
    public static final int INITIAL_CAR_SPAWN_COUNT = 5;

    // sizes
    public static final int CAR_WIDTH = 120;
    public static final int CAR_HEIGHT = 200;
    public static final int HATCH_WIDTH = 100;
    public static final int HATCH_HEIGHT = 100;
    public static final int SHAWARMA_WIDTH = 100;
    public static final int SHAWARMA_HEIGHT = 100;

    // spawn safe zones
    public static final int SAFE_SPAWN_ZONE_X = SCR_WIDTH / 2 - 100;
    public static final int SAFE_SPAWN_ZONE_Y = 50;
    public static final int SAFE_SPAWN_ZONE_WIDTH = 300;
    public static final int SAFE_SPAWN_ZONE_HEIGHT = 200;
    public static final int SAFE_SPAWN_X = SCR_WIDTH / 2 - 50;
    public static final int SAFE_SPAWN_Y = 100;
    public static final int SAFE_AREA_WIDTH = 200;

    // collision
    public static final float COLLISION_STRICT_FACTOR = 1.2f;
    public static final float COLLISION_LOOSE_FACTOR = 0.8f;

    // eat
    public static final int INITIAL_EAT = 100;
    public static final int EAT_DRAIN_PER_STEP = 2;
    public static final int EAT_GAIN_PER_SHAWARMA = 20;
    public static final int MAX_EAT = 100;
    public static final int EAT_RED_THRESHOLD = 30;
    public static final int EAT_YELLOW_THRESHOLD = 50;

    // biome
    public static final int DESERT_UNLOCK_SCORE = 300;
    public static final int WINTER_UNLOCK_SCORE = 600;

    // achievement
    public static final int ACHIEVEMENT_WALK_STEPS = 1000;
    public static final int ACHIEVEMENT_DESERT_SCORE = 400;
    public static final int ACHIEVEMENT_WINTER_SCORE = 800;

    // animation
    public static final int STEP_ANIMATION_FRAMES = 15;
    public static final int ANIMATION_FRAME_DIVISOR = 10;
    public static final int ANIMATION_FRAME_COUNT = 3;

    // pause menu
    public static final int PAUSE_BUTTON_SPACING = 60;
    public static final int PAUSE_BUTTON_HEIGHT = 50;
    public static final int PAUSE_BUTTON_OFFSET_X = 200;
    public static final int PAUSE_BUTTON_OFFSET_Y = -80;

    // movement buttons
    public static final int BUTTON_SIZE = 100;
    public static final int BUTTON_MARGIN = 30;

    // lane
    public static final int LANE_COUNT = 5;

    // deactivation
    public static final int HATCH_DEACTIVATE_X = -100;

    // volume
    public static final float VOLUME_STEP = 0.1f;
    public static final float VOLUME_BAR_WIDTH = 400;
    public static final float VOLUME_BAR_HEIGHT = 30;

    // button
    public static final int DEFAULT_BUTTON_WIDTH = 400;
    public static final int DEFAULT_BUTTON_HEIGHT = 150;

    // font scales
    public static final float FONT_SCALE_BUTTON = 5f;
    public static final float FONT_SCALE_COUNTER = 3f;
    public static final float FONT_SCALE_PAUSE = 2f;
    public static final float FONT_SCALE_SOUND = 1.5f;
    public static final float FONT_SCALE_WALK = 2f;
    public static final float FONT_SCALE_NOTIFICATION = 2.5f;
    public static final float FONT_SCALE_TITLE = 3f;
    public static final float FONT_SCALE_DESC = 1.5f;

    // preferences keys
    public static final String PREFS_NAME = "game_prefs";
    public static final String SCORE_KEY = "total_score";
    public static final String HIGH_SCORE_KEY = "high_score";
    public static final String USER_SAVES = "User saves";
    public static final String BEST_SCORE_KEY = "best_score";

    // notification
    public static final float NOTIFICATION_DURATION = 2.0f;
    public static final int NOTIFICATION_BG_WIDTH = 650;
    public static final int NOTIFICATION_BG_HEIGHT = 180;
    public static final int NOTIFICATION_BORDER_THICKNESS = 8;

    // shawarma and hatch reset
    public static final int HATCH_RESET_X_OFFSET = 300;
    public static final int HATCH_RESET_X_RANDOM = 200;
    public static final int SHAWARMA_RESET_X_OFFSET = 400;
    public static final int SHAWARMA_RESET_X_RANDOM = 200;

    // screen background clear colours
    public static final float CLEAR_COLOR_R = 0;
    public static final float CLEAR_COLOR_G = 0;
    public static final float CLEAR_COLOR_B = 0;
    public static final float CLEAR_COLOR_A = 1;

    // menu screen
    public static final int MENU_START_BTN_X = 440;
    public static final int MENU_START_BTN_Y = 300;
    public static final int MENU_SETTINGS_BTN_X = 440;
    public static final int MENU_SETTINGS_BTN_Y = 180;
    public static final int MENU_QUIT_BTN_X = 440;
    public static final int MENU_QUIT_BTN_Y = 50;
    public static final int MENU_COUNTER_MARGIN_LEFT = 100;
    public static final int MENU_COUNTER_MARGIN_BOTTOM = 100;
    public static final int MENU_COUNTER_SPACING = 70;

    // restart screen
    public static final int RESTART_BTN_X = 100;
    public static final int RESTART_BTN_Y = 400;
    public static final int RESTART_MENU_BTN_X = 100;
    public static final int RESTART_MENU_BTN_Y = 200;
    public static final int RESTART_COUNTER_X = 700;
    public static final int RESTART_COUNTER_Y = 620;

    // settings screen
    public static final int SETTINGS_BTN_X = 440;
    public static final int SETTINGS_BTN_1_Y = 460;
    public static final int SETTINGS_BTN_2_Y = 330;
    public static final int SETTINGS_BTN_3_Y = 200;
    public static final int SETTINGS_BTN_4_Y = 70;

    // sound screen
    public static final int SOUND_PLUS_BTN_X = 640;
    public static final int SOUND_PLUS_BTN_Y = 250;
    public static final int SOUND_MINUS_BTN_X = 250;
    public static final int SOUND_MINUS_BTN_Y = 250;
    public static final int SOUND_BACK_BTN_X = 440;
    public static final int SOUND_BACK_BTN_Y = 100;
    public static final float VOLUME_BAR_X = 440;
    public static final float VOLUME_BAR_Y = 500;
    public static final float VOLUME_LABEL_X = 450;
    public static final float VOLUME_LABEL_Y = 525;
    public static final float VOLUME_BAR_BG_R = 0.3f;
    public static final float VOLUME_BAR_BG_G = 0.3f;
    public static final float VOLUME_BAR_BG_B = 0.3f;
    public static final float VOLUME_BAR_BG_A = 1;
    public static final float VOLUME_BAR_FILL_R = 0;
    public static final float VOLUME_BAR_FILL_G = 0.8f;
    public static final float VOLUME_BAR_FILL_B = 0;
    public static final float VOLUME_BAR_FILL_A = 1;

    // achievement screen
    public static final int ACH_BACK_BTN_X = 440;
    public static final int ACH_BACK_BTN_Y = 60;
    public static final int ACH_TITLE_X = 400;
    public static final int ACH_TITLE_Y = 660;
    public static final int ACH_BADGE_X = 80;
    public static final int ACH_BADGE_SIZE = 80;
    public static final int ACH_BADGE_ROW_1_Y = 500;
    public static final int ACH_BADGE_ROW_2_Y = 350;
    public static final int ACH_BADGE_ROW_3_Y = 200;
    public static final int ACH_TEXT_X = 180;
    public static final float ACH_LOCKED_ALPHA = 0.5f;
    public static final float ACH_LOCKED_DIM = 0.6f;

    // skins screen
    public static final int SKINS_BTN_X = 200;
    public static final int SKINS_BTN_1_Y = 550;
    public static final int SKINS_BTN_2_Y = 400;
    public static final int SKINS_BTN_3_Y = 250;
    public static final int SKINS_BACK_BTN_X = 440;
    public static final int SKINS_BACK_BTN_Y = 50;
    public static final int SKINS_PREVIEW_X = 700;
    public static final int SKINS_PREVIEW_Y = 200;
    public static final int SKINS_PREVIEW_WIDTH = 500;
    public static final int SKINS_PREVIEW_HEIGHT = 400;

    // game HUD
    public static final int POINT_COUNTER_MARGIN_RIGHT = 300;
    public static final int POINT_COUNTER_MARGIN_TOP = 60;
    public static final int EAT_COUNTER_MARGIN_RIGHT = 900;
    public static final float GAME_CLEAR_R = 0.2f;
    public static final float GAME_CLEAR_G = 0.2f;
    public static final float GAME_CLEAR_B = 0.2f;

    // game pause button
    public static final float PAUSE_BTN_SCALE = 2.5f;

    // walk button positions
    public static final int WALK_JUMP_X = 1100;
    public static final int WALK_JUMP_Y = 100;
    public static final int WALK_UP_X = 1000;
    public static final int WALK_UP_Y = 150;
    public static final int WALK_DOWN_X = 1000;
    public static final int WALK_DOWN_Y = 50;

    // font scales (additional)
    public static final float FONT_SCALE_EAT = 3f;

    // notification colours
    public static final float NOTIF_BORDER_R = 0.9f;
    public static final float NOTIF_BORDER_G = 0.2f;
    public static final float NOTIF_BORDER_B = 0.2f;
    public static final float NOTIF_BORDER_A = 0.95f;
    public static final float NOTIF_FILL_R = 0;
    public static final float NOTIF_FILL_G = 0;
    public static final float NOTIF_FILL_B = 0;
    public static final float NOTIF_FILL_A = 0.85f;

    // lane
    public static final int LANE_WIDTH = SCR_WIDTH / 2;

}

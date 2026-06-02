package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class TextButton {

    public BitmapFont font;

    String text;
    public Texture texture;

    public int x;
    public int y;
    public int textX;
    public int textY;
    public int buttonWidth;
    public int buttonHeight;
    public int textWidth;
    public int textHeight;

    public TextButton(int x, int y, String text) {
        this.text = text;
        this.x = x;
        this.y = y;

        font = new BitmapFont();
        font.getData().setScale(5f);
        font.setColor(Color.WHITE);

        GlyphLayout gl = new GlyphLayout(font, text);
        textWidth = (int) gl.width;
        textHeight = (int) gl.height;

        texture = new Texture("backgrounds/button_bg.png");
        buttonWidth = 400;
        buttonHeight = 150;

        textX = x + (buttonWidth - textWidth) / 2;
        textY = y + (buttonHeight + textHeight) / 2;
    }


    public void draw(Batch batch) {
        batch.draw(texture, x, y, buttonWidth, buttonHeight);
        font.draw(batch, text, textX, textY);
    }

    public boolean isHint(int tx, int ty) {
        return (tx >= x && tx <= x + buttonWidth) && (ty >= y && ty <= y + buttonHeight);
    }

    public void dispose() {
        texture.dispose();
        font.dispose();
    }
}

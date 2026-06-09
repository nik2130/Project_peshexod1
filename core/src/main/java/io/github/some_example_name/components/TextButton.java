package io.github.some_example_name.components;

import io.github.some_example_name.Resurces;

public class TextButton extends BaseButton {

    public TextButton(int x, int y, String text) {
        super(x, y, text, Resurces.PATH_BUTTON_BG);
    }
}

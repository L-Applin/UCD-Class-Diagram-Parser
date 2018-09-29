package app;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class ShortcutController {

    public final static KeyCombination META_0 = new KeyCodeCombination(KeyCode.O, KeyCombination.META_DOWN);
    public final static KeyCombination ESC = new KeyCodeCombination(KeyCode.ESCAPE);

    private Scene scene;

    public ShortcutController(Scene scene) {
        this.scene = scene;
    }


}

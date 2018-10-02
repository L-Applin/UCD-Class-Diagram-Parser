package screenDisplay.components.umlComponents;

import javafx.scene.Node;

public interface UmlView {
    <T extends Node> T init();
}

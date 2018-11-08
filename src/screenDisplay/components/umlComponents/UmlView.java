package screenDisplay.components.umlComponents;

import javafx.scene.Node;

/**
 * An interface used to group custom Vuew element that uses JavaFX Node
 * element for display purpose.
 */
public interface UmlView {

    /**
     * Initialise the view. <p></p>
     * This method shoul set all parameters and values of the class the needs to be set
     * before displaying it to the screen.
     * @param <T> The type of Node that the View actually uses
     * @return should return itself to chain other operations.
     */
    <T extends Node> T init();
}

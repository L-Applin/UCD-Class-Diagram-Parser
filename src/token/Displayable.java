package token;

import screenDisplay.ScreenController;

/**
 * Item that can be displayed onto a {@link screenDisplay.MainDisplay} that can update the display information.
 */
public interface Displayable {

    /**
     * This method should return a single word, or very small text for representing the element.
     * @return The text to be display.
     */
    String display();

    /**
     * Required to transfer the action to the {@link ScreenController}. The screen controller should implement
     * a method to itself handle the event. Typical usage could be : <p></p>
     * <pre>
     * public void updateScreen(ScreenController controller) {
     *     controller.updateSelection(this);
     * }
     * </pre>
     * where updateSelection() does the heavy update work.
     *
     * @param controller the screen controller used to dispatch event.
     */
    void updateScreen(ScreenController controller);



}

package token;

import screenDisplay.ScreenController;

public interface Displayable {
    String display();
    void updateScreen(ScreenController controller);

}

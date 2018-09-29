package screenDisplay.components.umlComponents;

import app.theme.AppTheme;
import screenDisplay.components.BtnListView;
import token.UmlToken;

import java.util.Collection;

public abstract class UmlBtnListView extends BtnListView {

    protected Collection<UmlToken> listItem;

    public UmlBtnListView(AppTheme theme) {
        super(theme);
    }

}

package screenDisplay.components.umlComponents;

import app.theme.AppTheme;
import screenDisplay.components.BtnListView;
import token.UmlToken;

import java.util.Collection;
import java.util.List;

public abstract class UmlBtnListView extends BtnListView {

    protected List<UmlToken> listItem;
    public List<UmlToken> getListItem() { return listItem; }

    public UmlBtnListView(AppTheme theme) {
        super(theme);
    }

}

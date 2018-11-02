package screenDisplay.components.umlComponents;

import screenDisplay.theme.AppTheme;
import screenDisplay.components.BtnListView;
import token.UmlToken;

import java.util.List;

public abstract class UmlBtnListView extends BtnListView {

    protected List<? extends UmlToken> listItem;
    public List<? extends UmlToken> getListItem() { return listItem; }

    public UmlBtnListView(AppTheme theme) {
        super(theme);
    }

}

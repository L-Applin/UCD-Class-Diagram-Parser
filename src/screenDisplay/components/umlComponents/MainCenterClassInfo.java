package screenDisplay.components.umlComponents;

import screenDisplay.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import screenDisplay.MainDisplay;
import token.UmlClass;
import token.UmlToken;

import java.util.ArrayList;

import static screenDisplay.MainDisplay.*;

/**
 * Container for displayinf all infos of the selected
 */
public class MainCenterClassInfo extends GridPane {

    private static final Insets center_padding = new Insets(10,0,10,10);
    private static final int window_width = 450;

    private MainDisplay mainDisplay;

    private AppTheme theme;

    private ClassInfoListView attributsList, methodList, subClassList, assosIntegrList, metricList;
    private DetailsView detailView;

    public MainCenterClassInfo(AppTheme theme, MainDisplay main) {
        this.theme = theme;
        this.mainDisplay = main;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public MainCenterClassInfo init(UmlClass umlClass){

        setPadding(center_padding);
        setAlignment(Pos.TOP_CENTER);

        attributsList =     new ClassInfoListView(mainDisplay, umlClass.getAttributes());
        methodList =        new ClassInfoListView(mainDisplay, umlClass.getOperations());
        subClassList =      new ClassInfoListView(mainDisplay, umlClass.getSubClasses());
        assosIntegrList =   new ClassInfoListView(mainDisplay, umlClass.getAggAssocList());
        metricList =        new ClassInfoListView(mainDisplay, new ArrayList<>(umlClass.getMetrics().values()));
        detailView =        new DetailsView(mainDisplay);

        attributsList.setTitle(ATTR_TITLE);
        methodList.setTitle(OP_TITLE);
        subClassList.setTitle(SUBCLASS_TITLE);
        assosIntegrList.setTitle(ASSO_INTRG_TITLE);
        detailView.setTitle(DETAILS_TITLE);
        metricList.setTitle(METRIC_TITLE);

        methodList.overrideDefaultSize(new ClassInfoListView.Size(window_width, ClassInfoListView.default_height));
        assosIntegrList.overrideDefaultSize(new ClassInfoListView.Size(window_width, ClassInfoListView.default_height));
        metricList.overrideDefaultSize(new ClassInfoListView.Size(140, 280));
        metricList.getContainer().setFitToWidth(true);

        setMargin(attributsList, ClassInfoListView.list_container_padding);
        setMargin(methodList, ClassInfoListView.list_container_padding);
        setMargin(detailView, ClassInfoListView.list_container_padding);

        add(attributsList.init(), 0, 0);
        add(methodList.init(), 1, 0);
        add(subClassList.init(), 0,1);
        add(assosIntegrList.init(), 1,1);
        add(detailView.init(umlClass),0,2, 3, 1);
        add(metricList.init(), 2, 0, 1, 2);
        return this;
    }

    public void resetButtons(){
        attributsList.resetSelect();
        methodList.resetSelect();
        subClassList.resetSelect();
        assosIntegrList.resetSelect();
        metricList.resetSelect();
    }

    public void updateDetails(UmlToken token){
        detailView.updateDetails(token);
    }

}

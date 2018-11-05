package screenDisplay;

import javafx.application.Application;
import javafx.beans.NamedArg;
import javafx.stage.Stage;
import token.*;

/**
 * Controller class used for dispatchnig updates of the user interface.
 */
public class ScreenController {

    private Stage primaryStage;
    private Application.Parameters parameters;
    private MainDisplay main;

    public ScreenController(MainDisplay main) {
        this.main = main;
    }

    public ScreenController(Stage primaryStage, Application.Parameters parameters) {
        this.primaryStage = primaryStage;
        this.parameters = parameters;
    }

    /**
     * Launces the main display and starts the View.
     */
    public void setupMainDisplay(){

        // todo : if command line parameters has -f "path to file", open the file path directly
        main = new MainDisplay(primaryStage);
        main.init();
    }

    /**
     * Dispatch for UmlClass selection in the {@link MainDisplay}
     * @param umlClass the class that was selected
     */
    public void updateSelection(UmlClass umlClass){
        main.updateClassSelected(umlClass);
    }

    /**
     * Dispatch for UmlOperation selection in the {@link MainDisplay}
     * @param op the operation that was selected
     */
    public void updateSelection(UmlOperation op){
        main.updateTokenClicked(op);
    }

    /**
     * Dispatch for UmlAttribute selection in the {@link MainDisplay}
     * @param attr the Attribute that was selected
     */
    public void updateSelection(UmlAttribute attr){
        main.updateTokenClicked(attr);
    }

    /**
     * Dispatch for UmlAssociation selection in the {@link MainDisplay}
     * @param assoc the Association that was selected
     */
    public void updateSelection(UmlAssociation assoc){
        main.updateTokenClicked(assoc);
    }

    /**
     * Dispatch for UmlAggregation selection in the {@link MainDisplay}
     * @param aggreg the Aggregation that was selected
     */
    public void updateSelection(UmlAggregation aggreg){
        main.updateTokenClicked(aggreg);
    }

    /**
     * Dispatch for UmlMetric selection in the {@link MainDisplay}
     * @param metric the Metric that was selected
     */
    public void updateSelection(UmlMetric metric){
        main.updateTokenClicked(metric);

    }


}

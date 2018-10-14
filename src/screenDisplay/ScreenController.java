package screenDisplay;

import javafx.application.Application;
import javafx.stage.Stage;
import token.*;
import utils.Utils;

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

    public void setupMainDisplay(){

        // todo : if command line parameters has -f "path to file", open the file path directly
        main = new MainDisplay(primaryStage);
        main.init();
    }

    public void updateSelection(UmlClass umlClass){
        main.updateClassSelected(umlClass);
    }

    public void updateSelection(UmlOperation op){
        main.updateTokenClicked(op);
        //Utils.Log.test("UmlOperation : ", op.display());
    }

    public void updateSelection(UmlAttribute attr){
        main.updateTokenClicked(attr);
        //Utils.Log.test("UmlAttribute : ", attr.display());
    }

    public void updateSelection(UmlAssociation assoc){
        main.updateTokenClicked(assoc);
        //Utils.Log.test("UmlOperation : ", assoc.display());
    }

    public void updateSelection(UmlAggregation aggreg){
        main.updateTokenClicked(aggreg);
        //Utils.Log.test("UmlOperation : ", aggreg.display());
    }

}

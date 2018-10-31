package screenDisplay.components;

public interface ListItem {

    @FunctionalInterface
    interface BtnAction {
        void run(ListButton btn);
    }

}

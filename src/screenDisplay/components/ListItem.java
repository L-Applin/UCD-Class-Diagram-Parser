package screenDisplay.components;

public interface ListItem {

    @FunctionalInterface
    public interface BtnAction {
        void run(ListButton btn);
    }

}

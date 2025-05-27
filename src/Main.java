import controllers.MainController;
import views.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Ensure the GUI is created on the Event Dispatch Thread
            MainView mainView = new MainView();
            new MainController(mainView);
            mainView.setVisible(true);
    }
}

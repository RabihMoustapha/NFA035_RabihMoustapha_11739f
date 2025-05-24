package Main;
import Controllers.MainController;
import Views.AddContactsFrame;
import Views.MainFrame;

public class MainApp {
    public static void main(String[] args) {
            MainFrame mainFrame = new MainFrame();
            MainController controller = new MainController(mainFrame);
            mainFrame.setVisible(true);
    }
}

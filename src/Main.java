import controllers.MainController;
import views.MainView;

public class Main {
    public static void main(String[] args) {
            MainView mainView = new MainView();
            new MainController(mainView);
            mainView.setVisible(true);
    }
}

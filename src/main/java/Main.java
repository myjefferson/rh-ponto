import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Stage stage = primaryStage;
        Parent fxmlMain = FXMLLoader.load(getClass().getResource("/view/MainPonto.fxml"));
        Scene mainScene = new Scene(fxmlMain);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("RH Ponto");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) throws ClassNotFoundException {
        launch(args);
    }
}

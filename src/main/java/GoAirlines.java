import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GoAirlines extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/MainPane.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        //scene.setFill(Color.TRANSPARENT);


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

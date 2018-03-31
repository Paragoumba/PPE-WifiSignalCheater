package fr.paragoumba.ppe.wifisignalcheater;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WifiSignalCheater extends Application {

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();

        Controller.setController(fxmlLoader.getController());

        primaryStage.setScene(new Scene(root, 889, 500));
        primaryStage.setTitle("PPE - WifiSignalAnalyzer");
        primaryStage.setMaximized(true);
        primaryStage.setOnCloseRequest(event -> WifiRandomizer.running = false);
        primaryStage.show();

        WifiFactory.generateWifis();

    }
}

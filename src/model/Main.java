package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader root = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
        primaryStage.setTitle("Decimal-64 Floating Point Converter");
        primaryStage.setResizable(false);
        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/media/logo.png")));
        Scene scene = new Scene(root.load(),1188,638);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

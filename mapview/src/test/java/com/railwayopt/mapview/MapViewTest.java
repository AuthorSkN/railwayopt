package com.railwayopt.mapview;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;

public class MapViewTest extends Application {

    @Test
    public void loadMapViewTest(){
        launch(new String[0]);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("test_map.fxml"));
        TestController controller = new TestController();
        fxmlLoader.setController(controller);
        fxmlLoader.setClassLoader(TestController.class.getClassLoader());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setTitle("Тест");
        primaryStage.setScene(scene);
        primaryStage.show();
        controller.loadMap();
    }
}

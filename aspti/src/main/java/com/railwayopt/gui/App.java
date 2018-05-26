package com.railwayopt.gui;

import com.railwayopt.DB;
import com.railwayopt.exceptions.SystemException;
import javafx.application.*;
import javafx.stage.Stage;

public class App extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DB.initializer();
        SceneManager.init(primaryStage);
        try {
            SceneManager.installProjectsScene();
        } catch (SystemException exc) {
            SceneManager.showErrorMessage("Системная ошибка!","Свяжитесь с администратором.");
            Platform.exit();
            System.exit(0);
        }
    }
}

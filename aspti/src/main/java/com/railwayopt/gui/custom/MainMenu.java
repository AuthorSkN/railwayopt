package com.railwayopt.gui.custom;

import com.railwayopt.gui.SceneManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainMenu extends AnchorPane {

    private static final String FXML_COMPONENT_NAME = "scenes/main_menu.fxml";

    public MainMenu(){
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_COMPONENT_NAME));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
             fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void toSharedData(){
        SceneManager.installSharedDataScene();
    }
}

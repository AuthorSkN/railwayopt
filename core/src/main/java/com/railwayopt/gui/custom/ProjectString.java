package com.railwayopt.gui.custom;

import com.railwayopt.gui.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ProjectString extends HBox{

    private static final String FXML_COMPONENT_NAME = "scenes/project_string.fxml";

    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label stationCount;
    @FXML
    private Label factoryCount;
    @FXML
    private Label author;

    private int id;

    public ProjectString(int id, String name, String date, int stationCount, int factoryCount, String authorName){
        super();
        this.id= id;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_COMPONENT_NAME));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();
            this.name.setText(name);
            this.date.setText(date);
            this.stationCount.setText(Integer.toString(stationCount));
            this.factoryCount.setText(Integer.toString(factoryCount));
            this.author.setText(authorName);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public int getProjectId() {
        return id;
    }
}

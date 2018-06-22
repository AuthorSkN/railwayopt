package com.railwayopt.gui.custom;

import com.railwayopt.Solution;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class SolutionString extends HBox {

    private static final String FXML_COMPONENT_NAME = "scenes/solution_string.fxml";

    private int id;

    @FXML
    private Label countKP;
    @FXML
    private Label countKNRC;
    @FXML
    private Label date;

    public SolutionString(Solution solution){
        super();
        this.id= solution.getId();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_COMPONENT_NAME));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();
            this.date.setText(solution.getDate());
            this.countKP.setText(Integer.toString(solution.getAllKNRCId().size()));
            this.countKNRC.setText(Integer.toString(solution.getCountKP()));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public int getSolutionId() {
        return id;
    }
}

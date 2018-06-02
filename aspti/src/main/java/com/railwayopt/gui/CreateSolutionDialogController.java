package com.railwayopt.gui;

import com.railwayopt.entity.Factory;
import com.railwayopt.entity.Station;
import com.railwayopt.model.clustering.Element;
import com.railwayopt.model.clustering.kmeanspro.KProInitializer;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.Collection;

public class CreateSolutionDialogController implements Controller{

    @FXML
    private TextField textName;
    @FXML
    private TextArea textDesc;
    @FXML
    private TextField textCountKP;
    @FXML
    private TextField textCountKNRC;

    private Collection<Factory> factories;
    private Collection<Station> stations;
    private String name;
    private String desc;
    private int countKP;
    private int countKNRC;


    public void setObjects(Collection<Factory> factories, Collection<Station> stations){
        this.factories = factories;
        this.stations = stations;
    }

    @Override
    public void initializeScene() {

    }

    @FXML
    public void setMCOParameters(){
        this.name = textName.getText();
        this.desc = textDesc.getText();
        this.countKP = Integer.parseInt(textCountKP.getText());
        this.countKNRC = Integer.parseInt(textCountKNRC.getText());
        if (this.countKNRC == 0){
            createSolution();
        }
    }

    public void createSolution(){
        KProInitializer initializer = new KProInitializer();

    }

}

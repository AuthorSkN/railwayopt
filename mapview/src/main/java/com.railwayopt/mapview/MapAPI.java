package com.railwayopt.mapview;


import com.railwayopt.mapview.graphic.MapPolyline;
import com.railwayopt.mapview.graphic.MapPoint;
import javafx.beans.value.*;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

import java.net.URL;

public abstract class MapAPI {

    public static int MAP_POINT = 0;
    public static int MAP_LINE = 1;

    private WebEngine web;
    private boolean loadingMap = true;

    protected abstract URL getMapHTMLLocation();

    void setWebEngine(WebEngine webEngine){
        this.web = webEngine;
    }

    void loadMap(){
        web.load(getMapHTMLLocation().toExternalForm());
        loadingMap = true;
        web.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> ov, Worker.State t, Worker.State t1) {
                if (t1 == Worker.State.SUCCEEDED) {
                    loadingMap = false;
                }
            }
        });
    }

    protected JSObject getJSMap(){
        return (JSObject)web.executeScript("window");
    }

    public boolean isLoadedMap(){
        return !loadingMap;
    }

    public abstract void showObject(int objectId, int objectType);

    public abstract void hideObject(int objectId, int objectType);

    public abstract void addPoint(MapPoint point);

    public abstract void changePoint(MapPoint point);

    public abstract void deletePoint(int pointId);

    public abstract void addLine(MapPolyline line);

    public abstract void changeLine(MapPolyline line);

    public abstract void deleteLine(int lineId);
}

package com.railwayopt.mapview.googlemap;


import com.railwayopt.mapview.MapAPI;
import com.railwayopt.mapview.graphic.MapPolyline;
import com.railwayopt.mapview.graphic.MapPoint;
import netscape.javascript.JSObject;
import java.net.URL;

public class GoogleMapAPI extends MapAPI{

    private static final String GOOGLE_MAP_FILE_NAME = "webapi/map.html";

    @Override
    protected URL getMapHTMLLocation() {
        return this.getClass().getResource(GOOGLE_MAP_FILE_NAME);
    }

    @Override
    public void showObject(int objectId, int objectType) {
        JSObject map = getJSMap();
        map.call("showObject", objectId, objectType);
    }

    @Override
    public void hideObject(int objectId, int objectType) {
        JSObject map = getJSMap();
        map.call("hideObject", objectId, objectType);
    }

    @Override
    public void addPoint(MapPoint point) {
        JSObject map = getJSMap();
        map.call("addPoint", point);
    }

    public void changePoint(MapPoint point){
        JSObject map = getJSMap();
        map.call("changePoint", point);
    }

    @Override
    public void deletePoint(int pointId) {
        JSObject map = getJSMap();
        map.call("deletePoint", pointId);
    }

    @Override
    public void addLine(MapPolyline line) {
        JSObject map = getJSMap();
        map.call("addLine", line);
    }

    @Override
    public void changeLine(MapPolyline line) {
        JSObject map = getJSMap();
        map.call("changeLine", line);
    }

    @Override
    public void deleteLine(int lineId) {
        JSObject map = getJSMap();
        map.call("deleteLine", lineId);
    }


}

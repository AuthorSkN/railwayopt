package com.railwayopt.mapview.graphic;

import com.railwayopt.mapview.GeoPoint;
import com.railwayopt.mapview.MapAPI;
import com.railwayopt.mapview.MapView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Складнев Н.С.
 */
public class MapPolyline extends GraphObject{

    private String color = "#000000";
    private List<GeoPoint> points = new ArrayList<>(2);

    public MapPolyline(int id, MapView mapView){
        super(id, mapView.getMapAPI());
    }

    public MapPolyline(int id, MapView mapView, int weight, List<GeoPoint> points){
        this(id, mapView);
        this.points = points;
        this.weight = weight;
    }

    public MapPolyline(int id, MapView mapView, int weight, String color, List<GeoPoint> points){
        this(id, mapView, weight, points);
        this.color = color;
    }

    /**
     * Возврачает узловые точки линии.
     * @return координаты узловых точек
     */
    public List<GeoPoint> getPoints(){
        return points;
    }

    /**
     * Устанавливает координаты узловых точек линии.
     * @param points координаты узловых точек линии
     */
    public void setPoints(List<GeoPoint> points){
        this.points = points;
    }

    /**
     * Возвращает координаты первой и последней точек линии
     * @return координаты точек
     */
    public GeoPoint[] getEndPoints(){
        GeoPoint[] endPoints = new GeoPoint[2];
        endPoints[0] = points.get(0);
        endPoints[1] = points.get(points.size()-1);
        return endPoints;
    }

    /**
     * Задает коордринаты первой и последней точек линии
     * @param point1 координаты первой точки
     * @param point2 координаты последней точки
     */
    public void setEndPoints(GeoPoint point1, GeoPoint point2){
        points.set(0, point1);
        points.set(points.size()-1, point2);
    }

    /**
     * Возвращает цвет линии
     * @return значение цвета линии (в формате #ffffff)
     */
    public String getColor() {
        return color;
    }

    /**
     * Устанавливает значение цвета линии.
     * @param color значение цвета линии (в формате #ffffff)
     */
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void updateOnMap() {
        api.changeLine(this);
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        if(visible){
            api.showObject(this.id, MapAPI.MAP_LINE);
        }else{
            api.hideObject(this.id, MapAPI.MAP_LINE);
        }
    }

}

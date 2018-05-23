package com.railwayopt.mapview.graphic;

import com.railwayopt.mapview.GeoPoint;
import com.railwayopt.mapview.MapAPI;
import com.railwayopt.mapview.MapView;

/**
 * @author Складнев Н.С.
 */
public class MapPoint extends GraphObject {

    private GeoPoint coordinates;
    private MapPointStyle style = new MapPointStyle();


    public MapPoint(int id, MapView mapView, GeoPoint coordinates){
        super(id, mapView.getMapAPI());
        this.coordinates = coordinates;
    }

    public MapPoint(int id, MapView mapView, GeoPoint coordinates, int weight){
        this(id, mapView, coordinates);
        this.weight = weight;
    }

    public MapPoint(int id, MapView mapView, GeoPoint coordinates, int weight, MapPointStyle style){
        this(id, mapView, coordinates, weight);
        this.style = style;
    }

    /**
     * Возвращает георафические координаты точки (широту и долготу).
     * @return координаты точки
     */
    public GeoPoint getCoordinates() {
        return coordinates;
    }

    /**
     * Устанавливает георафические координаты точки (широту и долготу).
     * @param coordinates объект координат
     */
    public void setCoordinates(GeoPoint coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Возвращает стиль точки (фигура и цвета)
     * @return стиль точки
     */
    public MapPointStyle getStyle() {
        return style;
    }

    /**
     * Устанавливает стиль точки (фигура и цвета)
     * @param style стиль точки
     */
    public void setStyle(MapPointStyle style) {
        this.style = style;
    }


    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        if(visible){
            api.showObject(this.id, MapAPI.MAP_POINT);
        }else{
            api.hideObject(this.id, MapAPI.MAP_POINT);
        }
    }

    @Override
    public void updateOnMap() {
        api.changePoint(this);
    }
}

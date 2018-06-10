package com.railwayopt.mapview;


import com.railwayopt.mapview.event.MapEventListener;
import com.railwayopt.mapview.graphic.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.util.*;


/**
 * <p>Графический компонент карты.</p>
 * <p>Предназначен для отображения карты и навигации по ней, добавления меток и геометрических фигур, с целью повышения
 * информативности.</p>
 * @author Складнев Н.С.
 */
public class MapView extends BorderPane {

    private static final String FXML_COMPONENT_NAME = "mapview.fxml";

    @FXML
    private WebView webView;

    private MapAPI mapAPI;

    private Map<Integer, MapPoint> points;
    private Map<Integer, MapPolyline> lines;
    private Map<Integer, MapPointGroup> pointGroups;
    private Map<Integer, MapLineGroup> lineGroups;

    /**
     * Конструктор
     */
    public MapView(){
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

    /**
     * Задает API некоторой web-карты
     * @param mapAPI API web-карты
     */
    public void setMapAPI(MapAPI mapAPI){
        this.mapAPI = mapAPI;
        this.mapAPI.setWebEngine(webView.getEngine());
    }

    public MapAPI getMapAPI(){
        return mapAPI;
    }

    /**
     * Перезагружает карту
     */
    public void reloadMap(){
        points = new HashMap<>();
        lines = new HashMap<>();
        pointGroups = new HashMap<>();
        lineGroups = new HashMap<>();
        mapAPI.loadMap();
    }

    /**
     * Перезагружает карту в заданных координатах и с указанным масштабом
     * @param centre позиция центра карты
     * @param zoom коэффициент масштаба карты
     */
    public void reloadMap(GeoPoint centre, int zoom){
        points = new HashMap<>();
        lines = new HashMap<>();
        pointGroups = new HashMap<>();
        lineGroups = new HashMap<>();
        mapAPI.loadMap();
        mapAPI.setCentreAndZoom(centre, zoom);
    }

    /**
     * Задает координаты центра карты и масштаб
     * @param centre позиция центра карты
     * @param zoom коэффициент масштаба карты
     */
    public void setCentreAndZoomForMap(GeoPoint centre, int zoom){
        mapAPI.setCentreAndZoom(centre, zoom);
    }


    /**
     * Создает точку заданного стиля на карте.
     * @param id id точки
     * @param point координаты точки
     * @param weight вес точки
     * @return объект MapPoint
     */
    public MapPoint createMapPoint(int id, GeoPoint point, int weight){
        MapPoint mapPoint = null;
        if(!points.containsKey(id)) {
            mapPoint = new MapPoint(id, this, point, weight);
            points.put(id, mapPoint);
            mapAPI.addPoint(mapPoint);
        }else{
            //throw new AlreadyExistsMapObjException(point.getClass().toString(), point.getID());
        }
        return mapPoint;
    }

    /**
     * Создает точку заданного стиля на карте.
     * @param id id точки
     * @param point координаты точки
     * @param weight вес точки
     * @param style видимость точки
     * @return объект MapPoint
     */
    public MapPoint createMapPoint(int id, GeoPoint point, int weight, MapPointStyle style){
        MapPoint mapPoint = null;
        if(!points.containsKey(id)) {
            mapPoint = new MapPoint(id, this, point, weight, style);
            points.put(id, mapPoint);
            mapAPI.addPoint(mapPoint);
        }else{
            //throw new AlreadyExistsMapObjException(point.getClass().toString(), point.getID());
        }
        return mapPoint;
    }

    /**
     * Создает точку заданного стиля на карте.
     * @param id id точки
     * @param point координаты точки
     * @param weight вес точки
     * @param style видимость точки
     * @return объект MapPoint
     */
    public MapPoint createMapPoint(int id, String title, GeoPoint point, int weight, MapPointStyle style){
        MapPoint mapPoint = null;
        if(!points.containsKey(id)) {
            mapPoint = new MapPoint(id, this, point, weight, style);
            mapPoint.setTitle(title);
            points.put(id, mapPoint);
            mapAPI.addPoint(mapPoint);
        }else{
            //throw new AlreadyExistsMapObjException(point.getClass().toString(), point.getID());
        }
        return mapPoint;
    }

    /**
     * Возвращает точку на карте по id
     * @param id id точки
     * @return объект MapPoint
     */
    public MapPoint getMapPoint(int id){
        return points.get(id);
    }

    /**
     * Удаляет точку с карты по её id
     * @param pointId id точки на карте
     */
    public void deleteMapPoint(int pointId){
        mapAPI.deletePoint(pointId);
    }


    /**
     * Создает линию на карте
     * @param lineId lineId линии
     * @param weight вес линии
     * @param points узловые точки линии
     * @return объект, созданной линии, на карте
     */
    public MapPolyline createMapPolyline(int lineId, int weight, List<GeoPoint> points) {
        MapPolyline mapPolyline = null;
        if (!lines.containsKey(lineId)){
            mapPolyline = new MapPolyline(lineId, this, weight, points);
            lines.put(lineId, mapPolyline);
            mapAPI.addLine(mapPolyline);
        }else{
            //Exception
        }
        return mapPolyline;
    }

    /**
     * Возвращает линию, созданную на карте
     * @param lineId id линии
     * @return объект, созданной на карте линии
     */
    public MapPolyline getMapPolyline(int lineId){
        return lines.get(lineId);
    }

    /**
     * Создает линию на карте
     * @param lineId lineId линии
     * @param weight вес линии
     * @param color цвет линии
     * @param points узловые точки линии
     * @return объект, созданной линии, на карте
     */
    public MapPolyline createMapPolyline(int lineId, int weight, String color, List<GeoPoint> points){
        MapPolyline mapPolyline = null;
        if (!lines.containsKey(lineId)){
            mapPolyline = new MapPolyline(lineId, this, weight, color, points);
            lines.put(lineId, mapPolyline);
            mapAPI.addLine(mapPolyline);
        }else{
            //Exception
        }
        return mapPolyline;
    }

    /**
     * Удаляет линию с карты по её id
     * @param lineId id линии на карте
     */
    public void deleteMapPolyline(int lineId){
        mapAPI.deleteLine(lineId);
    }

    /**
     * Создает группу точек, из тех, что уже существуют на карте.
     * Группа позволяет скрывать, показывать и задавать стиль всем точкам группы одновременно.
     * @param groupId id группы
     * @param pointIds id точек группы
     * @return объект группы точек
     */
    public MapPointGroup createMapPointGroup(int groupId, List<Integer> pointIds){
        MapPointGroup group = null;
        if(!pointGroups.containsKey(groupId)){
            List<MapPoint> groupPoints = new ArrayList<>();
            for(int id: pointIds){
                groupPoints.add(points.get(id));
            }
            group = new MapPointGroup(groupId, groupPoints);
            pointGroups.put(groupId, group);
        }
        return group;
    }

    /**
     * Возвращает группу по id
     * @param groupId id группы
     * @return объект группы
     */
    public MapPointGroup getMapPointGroup(int groupId){
        return pointGroups.get(groupId);
    }

    /**
     * Удаляет группу, но не объекты в ней
     * @param groupId id группы
     */
    public void deletePointGroup(int groupId){
        MapPointGroup group = pointGroups.remove(groupId);
        group.clear();
    }

    /**
     * Создает группу линий, из тех, что уже существуют на карте.
     * Группа позволяет скрывать, показывать и задавать цвет всем линиям группы одновременно.
     * @param groupId id группы
     * @param lineIds id точек группы
     * @return объект группы линий
     */
    public MapLineGroup createMapLineGroup(int groupId, List<Integer> lineIds){
        MapLineGroup group = null;
        if(!lineGroups.containsKey(groupId)){
            List<MapPolyline> groupLines = new ArrayList<>();
            for(int id: lineIds){
                groupLines.add(lines.get(id));
            }
            group = new MapLineGroup(groupId, groupLines);
            lineGroups.put(groupId, group);
        }
        return group;
    }

    /**
     * Возвращает группу по id
     * @param groupId id группы
     * @return объект группы
     */
    public MapLineGroup getMapLineGroup(int groupId){
        return lineGroups.get(groupId);
    }

    /**
     * Удаляет группу, но не объекты в ней
     * @param groupId id группы
     */
    public void deleteLineGroup(int groupId){
        MapLineGroup group = lineGroups.remove(groupId);
        group.clear();
    }

    /**
     * Удаляет все точки с карты
     */
    public void deleteAllPoint(){
        for(Integer id: points.keySet()){
            this.deleteMapPoint(id);
        }
        points.clear();
        pointGroups.clear();
    }

    /**
     * Удаляет все линии с карты
     */
    public void deleteAllLines(){
        for(Integer id: lines.keySet()){
            this.deleteMapPolyline(id);
        }
        lines.clear();
        lineGroups.clear();
    }

    public void onLoadedMap(MapEventListener listener){
        mapAPI.setOnLoadedMapListener(listener);
    }

}




package com.railwayopt.mapview;

/**
 * Класс географических координат точки на карте - широта и долгота.
 * @author Складнев Н.С.
 */
public class GeoPoint {

    private double latitude;
    private double longitude;

    /**
     * Конструктор
     * @param latitude широта
     * @param longitude долгота
     */
    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Возвращает широту
     * @return значение широты в градусах
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Устанавливает значение широты точки
     * @param latitude значение широты в градусах
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    /**
     * Возвращает значение долготы
     * @return значение долготы в градусах
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Устанавливает значение долготы координаты
     * @param longitude значение долготы в градусах
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

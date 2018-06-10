package com.railwayopt.model.location;


import com.railwayopt.model.clustering.Element;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.coords.UTMCoord;

public class Point implements Cloneable{

    protected double x;
    protected double y;
    protected int zone;

    /**
     * Конструктор
     *
     */
    public Point(){}

    /**
     * Конструктор
     *
     * @param x  координата x
     * @param y  координата y
     */
    public Point( double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Вычисляет расстояние между двумя точками
     *
     * @return расстояние
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        double deltaX = x1 - x2;
        double deltaY = y1 - y2;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * Вычисляет расстояние от данного элемента до переданного
     *
     * @param point элемент до которого расчитывается расстояние
     * @return растояние между элементами
     */
    public double distanceTo(Point point) {
        return Element.distance(this.x, this.y, point.getX(), point.getY());
    }

    /**
     * Вычисляет расстояние от данного элемента до переданной точки
     *
     * @param x координата x
     * @param y координата y
     * @return расстояние от элемента до точки
     */
    public double distanceTo(double x, double y) {
        return Element.distance(this.x, this.y, x, y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setCoorinatesByLatLon(double latitude, double longitude){
        UTMCoord coords = UTMCoord.fromLatLon(Angle.fromDegreesLatitude(latitude), Angle.fromDegreesLongitude(longitude));
        this.x = /*coords.getEasting()*/ longitude;
        this.y =/* coords.getNorthing();*/ latitude;
        this.zone = coords.getZone();
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

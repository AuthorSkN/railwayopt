package com.railwayopt.model.clustering;

public class Element extends Point {

    protected int id;
    protected double weight;

    /**
     * Конструктор
     *
     * @param id идентификатор
     */
    public Element(int id) {
        this.id = id;
    }

    /**
     * Конструктор
     *
     * @param id идентификатор
     * @param x  координата x
     * @param y  координата y
     */
    public Element(int id, double x, double y) {
        super(x,y);
        this.id = id;
    }

    /**
     * Конструктор
     *
     * @param id     идентификатор
     * @param x      координата x
     * @param y      координата y
     * @param weight вес элемента
     */
    public Element(int id, double x, double y, double weight) {
        super(x,y);
        this.id = id;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object elem) {
        if (this == elem) return true;
        if (!(elem instanceof Element)) return false;

        Element element = (Element) elem;

        if (getId() != element.getId()) return false;
        if (getX() != element.getX()) return false;
        if (getY() != element.getY()) return false;
        return Double.compare(element.getWeight(), getWeight()) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId();
        temp = Double.doubleToLongBits(getWeight());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getX());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getY());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


}

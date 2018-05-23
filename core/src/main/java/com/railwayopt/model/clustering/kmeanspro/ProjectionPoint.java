package com.railwayopt.model.clustering.kmeanspro;


import com.railwayopt.model.clustering.Element;

public class ProjectionPoint extends Element{

    private boolean isStaticCentre;

    /**
     * Конструктор
     *
     * @param id идентификатор
     */
    public ProjectionPoint(int id) {
        super(id);
    }

    /**
     * Конструктор
     *
     * @param id идентификатор
     * @param x  координата x
     * @param y  координата y
     */
    public ProjectionPoint(int id, double x, double y) {
        super(id, x, y);
    }

    /**
     * Конструктор
     *
     * @param id     идентификатор
     * @param x      координата x
     * @param y      координата y
     * @param weight вес элемента
     */
    public ProjectionPoint(int id, double x, double y, double weight) {
        super(id, x, y, weight);
    }

    public ProjectionPoint(int id, boolean isStaticCentre){
        super(id);
        this.isStaticCentre = isStaticCentre;
    }

    public boolean isStaticCentre() {
        return isStaticCentre;
    }

    public void setIsStaticCentre(boolean projection) {
        isStaticCentre = projection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectionPoint)) return false;
        if (!super.equals(o)) return false;

        ProjectionPoint that = (ProjectionPoint) o;
        if (isStaticCentre() != that.isStaticCentre()) return false;
        if (getId() != that.getId()) return false;
        if (Double.compare(that.getWeight(), getWeight()) != 0) return false;
        if (Double.compare(that.getX(), getX()) != 0) return false;
        return Double.compare(that.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (isStaticCentre() ? 1 : 0);
        result = 31 * result + getId();
        temp = Double.doubleToLongBits(getWeight());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getX());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getY());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

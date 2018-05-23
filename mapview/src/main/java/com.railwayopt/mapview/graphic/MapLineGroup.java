package com.railwayopt.mapview.graphic;


import java.util.List;

/**
 * @author Складнев Н.С.
 */
public class MapLineGroup extends MapGroup<MapPolyline>{

    private String color;

    public MapLineGroup(int id, List<MapPolyline> objects) {
        super(id, objects);
    }

    /**
     * Возврвщвет значение цвета линий в группе.
     * @return значение цвета (в формате #ffffff)
     */
    public String getColor() {
        return color;
    }

    /**
     * Устанавливает значение цвета в группе.
     * @param color значение цвета (в формате #ffffff)
     */
    public void setColor(String color) {
        this.color = color;
        for(MapPolyline groupObject: objects){
            groupObject.setColor(color);
        }
    }
}

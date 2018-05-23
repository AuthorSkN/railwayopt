package com.railwayopt.mapview.graphic;


import java.util.List;

/**
 * @author Складнев Н.С.
 */
public class MapPointGroup extends MapGroup<MapPoint>{

    private MapPointStyle style;

    public MapPointGroup(int id, List<MapPoint> objects) {
        super(id, objects);
    }

    /**
     * Возвращает стиль группы точек.
     * @return стиль группы(форма и цвета)
     */
    public MapPointStyle getStyle() {
        return style;
    }

    /**
     * Устанавливает стиль точек в группе.
     * @param style стиль точек(форма и цвета)
     */
    public void setStyle(MapPointStyle style) {
        this.style = style;
        for(MapPoint groupObject: objects){
            groupObject.setStyle(style);
        }
    }
}

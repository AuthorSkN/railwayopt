package com.railwayopt.mapview.graphic;


import com.railwayopt.mapview.MapAPI;
import com.railwayopt.mapview.MapView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Складнев Н.С.
 * @param <T> тип графического объекта
 */
public abstract class MapGroup<T extends GraphObject> {

    protected int id;
    protected List<T> objects;
    protected int weight;
    protected boolean visible;


    public MapGroup(int id, List<T> objects){
        this.id = id;
        this.objects = objects;
        for(T groupObject: objects){
            groupObject.addGroupId(id);
        }
    }

    /**
     * Добавляет графический объект в группу
     * @param groupObject грфический объект
     */
    public void add(T groupObject){
        objects.add(groupObject);
        groupObject.addGroupId(id);
    }

    /**
     * Удаляет грфический объект из группы
     * @param groupObject графияеский объект
     */
    public void remove(T groupObject){
        objects.remove(groupObject);
        groupObject.removeGroupId(id);
    }

    /**
     * Возвращает id всех объектов в группе. Сами объекты можно получить из контейнера MapView
     * @return список id объектов
     */
    public List<Integer> getObjectIds() {
        List<Integer> ids = new ArrayList<>(objects.size());
        for(T groupObject: objects){
            ids.add(groupObject.getId());
        }
        return ids;
    }

    /**
     * Обновление всех объектов из группы в соответсвии с параметрами группы.
     */
    public void updateOnMap(){
        for(T groupObject: objects){
            groupObject.updateOnMap();
        }
    }

    /**
     * Проверяет видимость группы в данный момент
     * @return true - если объект отображается на карте, false инче
     */
    public boolean isVisible() {
        return visible;
    }


    /**
     * Устанаавливает значение видимости группы на карте. Обновление происходит незамедлительно.
     * @param visible значение видимости
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        for(T groupObject: objects){
            groupObject.setVisible(visible);
        }
    }

    /**
     * Возвращает вес(толщину) графических объектов группы
     * @return значение веса
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Устанавливает значение веса(толщины) графических объектов группы
     * @param weight значение веса
     */
    public void setWeight(int weight) {
        this.weight = weight;
        for(T groupObject: objects){
            groupObject.setWeight(weight);
        }
    }

    /**
     * Удаляет все объекты из группы. (при этом сами объекты не удаляются)
     */
    public void clear(){
        for(T groupObject: objects){
            groupObject.removeGroupId(id);
        }
        objects.clear();
    }

}

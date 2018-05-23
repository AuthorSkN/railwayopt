package com.railwayopt.mapview.graphic;

import com.railwayopt.mapview.MapAPI;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Складнев Н.С.
 */
public abstract class GraphObject {

    protected MapAPI api;
    protected int id;
    protected boolean visible = true;
    protected int weight = 2;
    protected String title = "неизвестно";
    protected String desc = "отсутствует";
    protected List<Integer> groups = new LinkedList<>();

    public GraphObject(int id, MapAPI api){
        this.api = api;
        this.id = id;
    }

    /**
     * Возвращает id графического объекта
     * @return id объекта
     */
    public int getId() {
        return id;
    }

    /**
     * Проверяет видимость графического объекта в данный момент
     * @return true - если объект отображается на карте, false инче
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Устанаавливает значение видимости объекта на карте. Обновление графического объекта происходит незамедлительно.
     * @param visible значение видимости
     */
    public abstract void setVisible(boolean visible);

    /**
     * Возвращает вес(толщину) графического объекта
     * @return значение веса
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Устанавливает значение веса(толщины) графического объекта
     * @param weight значение веса
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Возвращает название объекта (отображается в подсказке при наведении).
     * @return название объекта
     */
    public String getTitle() {
        return title;
    }

    /**
     * Устанавливает название объекта (отображается в подсказке при наведении).
     * @param title название объекта
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Возвращает описание объекта.
     * @return текст описания
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Устанавливает текст описания объекта.
     * @param desc текст описания
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Обновляет объект на карте в соответствии с последними заданными параметрами.
     */
    public abstract void updateOnMap();

    void addGroupId(int groupId){
        if(!groups.contains(groupId)){
            groups.add(groupId);
        }
    }

    void removeGroupId(int groupId){
        groups.remove(groupId);
    }

}

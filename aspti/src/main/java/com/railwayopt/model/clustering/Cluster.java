package com.railwayopt.model.clustering;

import com.railwayopt.model.location.Point;

import java.util.*;

public class Cluster implements Iterable<Element>, Cloneable {

    protected Map<Integer, Element> elementsMap;

    public Cluster(){
        elementsMap = new HashMap<>();
    }

    /**
     * Возвращает сумарный вес кластера
     * @return вес кластера
     */
    public double getClusterWeight(){
        double sum = 0;
        Collection<Element> elements = elementsMap.values();
        for(Element el: elements){
            sum += el.getWeight();
        }
        return sum;
    }

    @Override
    public Iterator<Element> iterator() {
        return elementsMap.values().iterator();
    }

    public void setElements(Collection<Element> elements) {
        elementsMap.clear();
        for(Element element: elements){
            elementsMap.put(element.getId(), element);
        }
    }

    public void addElement(Element element){
        elementsMap.put(element.getId(), element);
    }

    public Element removeElementById(int elementId){
        return elementsMap.remove(elementId);
    }

    public Point getRealCentre(){
        double xCentre = 0;
        double yCentre = 0;
        for(Element element: elementsMap.values()){
            xCentre += element.getX();
            yCentre += element.getY();
        }
        return new Point(xCentre / elementsMap.keySet().size(), yCentre / elementsMap.keySet().size());
    }

    public Point getCentre(){
        return getRealCentre();
    }

    public int getSize(){
        return elementsMap.size();
    }

    public void clear(){
        elementsMap.clear();
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        Cluster cluster = (Cluster)super.clone();
        List<Element> elements = new LinkedList<>();
        for(Element element: this){
            elements.add((Element)element.clone());
        }
        cluster.setElements(elements);
        return cluster;
    }

    @Override
    public boolean equals(Object cl) {
        if (cl == null) return false;
        if (this == cl) return true;
        if (!(cl instanceof Cluster)) return false;

        Cluster otherCluster = (Cluster) cl;

        for(Element oel: otherCluster){
            Element el = elementsMap.get(oel.getId());
            if(!oel.equals(el)){
                return false;
            }
        }

        return true;
    }

    public List<Element> getElements(){
        return new ArrayList<>(elementsMap.values());
    }

    @Override
    public int hashCode() {
        return elementsMap.hashCode();
    }


}

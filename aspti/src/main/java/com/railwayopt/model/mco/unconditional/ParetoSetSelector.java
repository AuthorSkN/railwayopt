package com.railwayopt.model.mco.unconditional;

import com.railwayopt.exceptions.RailwayOptException;
import com.railwayopt.model.mco.CriteriaComparator;

import java.util.*;

public class ParetoSetSelector {

    private List<Criterion> criteria;

    public ParetoSetSelector(List<Criterion> criteria){
        this.criteria = criteria;
    }

    public List<Criterion> getCriteriaOfSelector(){
        return criteria;
    }

    public Set<Optimizable> getParetoSet(Set<? extends Optimizable> objects) throws RailwayOptException{
        Set<Optimizable> paretoSet = new HashSet<>();
        Set<Optimizable> restObjects = new HashSet<>(objects);
        while(restObjects.size() != 0){
            Map<Criterion, Optimizable> paretoObjectsForRest = new HashMap<>();
            //Поиск точек Парето для каждого критерия из оставшегося множества
            for(Criterion criterion: criteria){
                Optimizable paretoObject = getParetoObjectForCriterion(restObjects, criterion);
                paretoObjectsForRest.put(criterion, paretoObject);
                //restObjects.remove(paretoObject);
            }
            restObjects.removeAll(paretoObjectsForRest.values());
            //Отсечения из оставшегося множества безусловно плохих вариантов
            CriteriaComparator comporator = new CriteriaComparator(criteria);
            for(Criterion criterion: criteria){
                Optimizable pareto = paretoObjectsForRest.get(criterion);
                List<Optimizable> removeList = new LinkedList<>();
                for(Optimizable object: restObjects){
                    if(comporator.definitelyWorse(pareto, object)){
                        removeList.add(object);
                    }
                }
                restObjects.removeAll(removeList);
            }
            //Добавление полученных точек Парето в результирующее множество
            paretoSet.addAll(paretoObjectsForRest.values());
        }
        return paretoSet;
    }


    private Optimizable getParetoObjectForCriterion(Set<Optimizable> objects, Criterion criterion){
        Optimizable paretoObject = null;
        CriteriaComparator criteriaComparator = new CriteriaComparator(criteria);
        for(Optimizable object: objects){
            if((paretoObject == null)||(criteriaComparator.compareByCriterion(object, paretoObject, criterion) > 0)){
                paretoObject = object;
            }
        }
        return paretoObject;
    }






}

package com.railwayopt.model.mco;

import com.railwayopt.model.mco.unconditional.*;

import java.util.*;

public class CriteriaComparator implements Comparator<Optimizable>{

    private class CycleCriteria implements Iterable<Criterion>{
        private List<Criterion> criteria;
        private int idx = 0;
        private int initialIdx;

        public CycleCriteria(List<Criterion> criteria, Criterion initialCriterion){
            this.criteria = new ArrayList<>(criteria);
            while(criteria.get(idx).getId() != initialCriterion.getId()){
                idx++;
            }
            initialIdx = idx;
        }


        @Override
        public Iterator<Criterion> iterator() {
            return new Iterator<Criterion>() {

                private boolean isStart = true;

                @Override
                public boolean hasNext() {
                    if(isStart) {
                        isStart = false;
                        return true;
                    }else {
                        return (idx != initialIdx);
                    }
                }

                @Override
                public void remove() {}

                @Override
                public Criterion next() {
                    Criterion criterion = criteria.get(idx);
                    idx = ((idx + 1) == criteria.size())? 0 : idx + 1;
                    return criterion;
                }
            };
        }


    }


    private List<Criterion> criteria;

    /**
     * Конструктор
     * @param criteria критерии сравнения
     */
    public CriteriaComparator(List<Criterion> criteria){
        this.criteria = criteria;
    }

    /**
     * Выполняет абсолютное сравнение по всем критериям
     * @param o1 первый объект
     * @param o2 второй объект
     * @return 1 - если первый объект абсолютно больше, -1 - если первый объект абсолютно меньше, 0 если равны или несравнимы
     */
    @Override
    public int compare(Optimizable o1, Optimizable o2) {
        int criteriaSize = criteria.size();
        Map<Criterion, Double> objCriteriaVals1 = o1.parametersMapping(criteria);
        Map<Criterion, Double> objCriteriaVals2 = o2.parametersMapping(criteria);
        int moreCount = 0, lessCount = 0, equalsCount = 0;
        for(Criterion criteria: criteria){
            if(objCriteriaVals1.get(criteria) >  objCriteriaVals2.get(criteria)){
                moreCount++;
            } else if(objCriteriaVals1.get(criteria) <  objCriteriaVals2.get(criteria)){
                lessCount++;
            } else {
                equalsCount++;
            }
        }
        if (((moreCount+equalsCount) == criteriaSize)&&(moreCount > 0)) return 1;
        if (((lessCount+equalsCount) == criteriaSize)&&(lessCount > 0)) return -1;
        return 0;
    }


    /**
     * Определяет, является ли второй объект абсолютно хуже первого
     * @param object базовый объект
     * @param compareObject сравниваемый объект
     * @return true - если второй объект безусловно хуже первого
     */
    public boolean definitelyWorse(Optimizable object, Optimizable compareObject){
        return (compare(compareObject, object) < 0);
    }

    /**
     * Сравнивает объекты по конкетному критерию
     * @param o1 первый объект
     * @param o2 второй объект
     * @param compareCriterion критерий сравнения
     * @return 1 - если первый объект больше второго по критерию, -1 - если первый объект меньше второго по критерию, 0 - если абсолютно равны
     */
    public int compareByCriterion(Optimizable o1, Optimizable o2, Criterion compareCriterion){
        int result = 0;
        Map<Criterion, Double> objCriteriaVals1 = o1.parametersMapping(criteria);
        Map<Criterion, Double> objCriteriaVals2 = o2.parametersMapping(criteria);
        boolean hasTheBest = false;
        Iterator<Criterion> criteriaIterator = (new CycleCriteria(criteria, compareCriterion)).iterator();
        while(criteriaIterator.hasNext() && !hasTheBest){
            Criterion criterion = criteriaIterator.next();
            if(objCriteriaVals1.get(criterion) > objCriteriaVals2.get(criterion)){
                hasTheBest = true;
                result = 1;
            }else if (objCriteriaVals1.get(criterion) < objCriteriaVals2.get(criterion)){
                hasTheBest = true;
                result = -1;
            }
        }
        return result;
    }


}

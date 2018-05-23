package com.railwayopt.model.mco;

import com.railwayopt.model.mco.unconditional.*;
import jdk.nashorn.internal.AssertsEnabled;
import org.junit.*;

import java.util.*;

public class CriteriaComparatorTest
{

    private List<Criterion> criteria;
    private OptimizableObject object1 = new OptimizableObject(1);
    private OptimizableObject object2 =  new OptimizableObject(2);
    private OptimizableObject object3 =  new OptimizableObject(3);
    private OptimizableObject object1111 =  new OptimizableObject(1111);
    private CriteriaComparator criteriaComparator;

    private void setValueForCriteria(OptimizableObject object, double... values){
        for(int i = 0; i < criteria.size(); i++){
            object.setParameter(criteria.get(i), values[i]);
        }
    }

    @Before
    public void setUp(){
        this.criteria = Arrays.asList(new Criterion(0, "criteria0", Criterion.MAX_OPTIMUM_DIRECTION),
                new Criterion(1, "criterion1", Criterion.MIN_OPTIMUM_DIRECTION),
                new Criterion(2, "criterion2", Criterion.MAX_OPTIMUM_DIRECTION),
                new Criterion(3, "criterion3", Criterion.MAX_OPTIMUM_DIRECTION)
        );
        setValueForCriteria(object1, 3, 0, 6, 1);
        setValueForCriteria(object2, 2, 3, 4, 2);
        setValueForCriteria(object3, 1, 5, 0, 1);
        setValueForCriteria(object1111, 1, 1, 1, 1);
        this.criteriaComparator = new CriteriaComparator(criteria);
    }

    @Test
    public void compareTest(){
        Assert.assertEquals(criteriaComparator.compare(object1, object1111), 1);
        Assert.assertEquals(criteriaComparator.compare(object1, object2), 0);
        Assert.assertEquals(criteriaComparator.compare(object3, object1111), -1);
    }

    @Test
    public void definitelyWorseTest(){
        Assert.assertFalse(criteriaComparator.definitelyWorse(object3, object1111));
        Assert.assertTrue(criteriaComparator.definitelyWorse(object1, object3));
    }

    @Test
    public void compareByCriterionTest(){
        Assert.assertEquals(criteriaComparator.compareByCriterion(object1, object2, criteria.get(2)), 1);
        Assert.assertEquals(criteriaComparator.compareByCriterion(object3, object1, criteria.get(3)), -1);
        Assert.assertEquals(criteriaComparator.compareByCriterion(object1111, object1111, criteria.get(3)), 0);
    }
}

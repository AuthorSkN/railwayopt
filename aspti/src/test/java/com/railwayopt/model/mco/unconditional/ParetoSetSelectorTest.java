package com.railwayopt.model.mco.unconditional;

import org.junit.*;

import java.util.*;

public class ParetoSetSelectorTest {

    private List<Criterion> criteriaForLongCriteriaTest;
    private List<OptimizableObject> objectsForLongCriteriaTest;

    private List<Criterion> criteriaForDifDirectionTest;
    private List<OptimizableObject> objectsForDifDirectionTest;

    private void setValueForCriteria(OptimizableObject object,List<Criterion> criteria, double... values){
        for(int i = 0; i < criteria.size(); i++){
            object.setParameter(criteria.get(i), values[i]);
        }
    }

    @Before
    public void setUp(){
        ///////////////////////////////////////////////////////////////////////////////
        this.criteriaForLongCriteriaTest = Arrays.asList(
                new Criterion(0, "criteria0", Criterion.MAX_OPTIMUM_DIRECTION),
                new Criterion(1, "criterion1", Criterion.MAX_OPTIMUM_DIRECTION),
                new Criterion(2, "criterion2", Criterion.MAX_OPTIMUM_DIRECTION),
                new Criterion(3, "criterion3", Criterion.MAX_OPTIMUM_DIRECTION)
        );

        this.objectsForLongCriteriaTest = new ArrayList<>(6);
        for(int i = 0; i < 6; i++){
            objectsForLongCriteriaTest.add(new OptimizableObject(i));
        }
        setValueForCriteria(objectsForLongCriteriaTest.get(0), criteriaForLongCriteriaTest, 1.0, 1.0, 1.0, 1.0);
        setValueForCriteria(objectsForLongCriteriaTest.get(1), criteriaForLongCriteriaTest, 2.0, 3.0, 4.0, 0.0);
        setValueForCriteria(objectsForLongCriteriaTest.get(2), criteriaForLongCriteriaTest, 4.0, 1.0, 0.0, 2.0);
        setValueForCriteria(objectsForLongCriteriaTest.get(3), criteriaForLongCriteriaTest, 2.0, 2.0, 1.0, 2.0);
        setValueForCriteria(objectsForLongCriteriaTest.get(4), criteriaForLongCriteriaTest, 4.0, 1.0, 0.0, 0.0);
        setValueForCriteria(objectsForLongCriteriaTest.get(5), criteriaForLongCriteriaTest, 3.0, 0.0, 5.0, 5.0);
        ////////////////////////////////////////////////////////////////////////////////
        this.criteriaForDifDirectionTest = Arrays.asList(
                new Criterion(0, "criteria0", Criterion.MIN_OPTIMUM_DIRECTION),
                new Criterion(1, "criterion1", Criterion.MAX_OPTIMUM_DIRECTION),
                new Criterion(2, "criterion2", Criterion.MIN_OPTIMUM_DIRECTION)
        );

        this.objectsForDifDirectionTest = new ArrayList<>(8);
        for(int i = 0; i < 8; i++){
            objectsForDifDirectionTest.add(new OptimizableObject(i));
        }
        setValueForCriteria(objectsForDifDirectionTest.get(0), criteriaForDifDirectionTest, 0.0, 3.0, 5.0);
        setValueForCriteria(objectsForDifDirectionTest.get(1), criteriaForDifDirectionTest, 1.0, 4.0, 2.0);
        setValueForCriteria(objectsForDifDirectionTest.get(2), criteriaForDifDirectionTest, 1.0, 3.0, 0.0);
        setValueForCriteria(objectsForDifDirectionTest.get(3), criteriaForDifDirectionTest, 0.0, 0.0, 3.0);
        setValueForCriteria(objectsForDifDirectionTest.get(4), criteriaForDifDirectionTest, 4.0, 3.0, 1.0);
        setValueForCriteria(objectsForDifDirectionTest.get(5), criteriaForDifDirectionTest, 2.0, 5.0, 0.0);
        setValueForCriteria(objectsForDifDirectionTest.get(6), criteriaForDifDirectionTest, 3.0, 2.0, 1.0);
        setValueForCriteria(objectsForDifDirectionTest.get(7), criteriaForDifDirectionTest, 5.0, 4.0, 2.0);
    }


    @Test
    public void getParetoSetLongCriteriaTest(){
        ParetoSetSelector paretoSelector = new ParetoSetSelector(criteriaForLongCriteriaTest);
        Set<Optimizable> paretoSet = paretoSelector.getParetoSet(new HashSet(objectsForLongCriteriaTest));
        Set<Optimizable> correctSet = new HashSet<>();
        correctSet.add(objectsForLongCriteriaTest.get(1));
        correctSet.add(objectsForLongCriteriaTest.get(2));
        correctSet.add(objectsForLongCriteriaTest.get(3));
        correctSet.add(objectsForLongCriteriaTest.get(5));
        Assert.assertEquals(paretoSet, correctSet);
    }


    @Test
    public void getParetoSetForDifDirectionTest(){
        ParetoSetSelector paretoSelector = new ParetoSetSelector(criteriaForDifDirectionTest);
        Set<Optimizable> paretoSet = paretoSelector.getParetoSet(new HashSet(objectsForDifDirectionTest));
        Set<Optimizable> correctSet = new HashSet<>();
        correctSet.add(objectsForDifDirectionTest.get(0));
        correctSet.add(objectsForDifDirectionTest.get(1));
        correctSet.add(objectsForDifDirectionTest.get(2));
        correctSet.add(objectsForDifDirectionTest.get(3));
        correctSet.add(objectsForDifDirectionTest.get(5));
        Assert.assertEquals(paretoSet, correctSet);
    }

}

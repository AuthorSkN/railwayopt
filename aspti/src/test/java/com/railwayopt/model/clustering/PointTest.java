package com.railwayopt.model.clustering;

import com.railwayopt.model.location.Point;
import org.junit.Assert;
import org.junit.Test;

public class PointTest {

    @Test
    public void distanceToTest(){
        Point point1 = new Point(1,2);
        Point point2 = new Point(5,5);
        Assert.assertEquals((Double)point1.distanceTo(point2), Double.valueOf(5));
    }

}

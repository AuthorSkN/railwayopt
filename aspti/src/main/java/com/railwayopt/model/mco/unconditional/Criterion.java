package com.railwayopt.model.mco.unconditional;

import java.util.Objects;

public class Criterion
{

    public static final boolean MAX_OPTIMUM_DIRECTION = true;
    public static final boolean MIN_OPTIMUM_DIRECTION = false;

    private int id;
    private String name;
    private boolean optimumDirection;

    public Criterion(int id)
    {
        this.id = id;
    }

    public Criterion(int id, String name, boolean optimumDirection)
    {
        this.id = id;
        this.name = name;
        this.optimumDirection = optimumDirection;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean getOptimumDirection()
    {
        return optimumDirection;
    }

    public void setOptimumDirection(boolean optimumDirection)
    {
        this.optimumDirection = optimumDirection;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Criterion criterion = (Criterion)o;
        return id == criterion.id &&
                optimumDirection == criterion.optimumDirection &&
                Objects.equals(name, criterion.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, optimumDirection);
    }
}

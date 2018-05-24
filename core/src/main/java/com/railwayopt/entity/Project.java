package com.railwayopt.entity;

import java.util.List;
import java.util.Set;

public class Project {

    private int id;
    private String name;
    private String description;
    private Set<Station> stations;
    private Set<Factory> factories;
    private Set<Solution> solutions;

    public Project() {
    }

    public Project(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Station> getStations() {
        return stations;
    }

    public void setStations(Set<Station> stations) {
        this.stations = stations;
    }

    public Set<Factory> getFactories() {
        return factories;
    }

    public void setFactories(Set<Factory> factories) {
        this.factories = factories;
    }

    public Set<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(Set<Solution> solutions) {
        this.solutions = solutions;
    }
}

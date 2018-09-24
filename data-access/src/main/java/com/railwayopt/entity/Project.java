package com.railwayopt.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="project")
public class Project {

    @Id
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="descr")
    private String description;
    @Column(name="create_date")
    private String date;
    @Column(name="user_name")
    private String author;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Station> station = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Factory> factory = new HashSet<>();

    public Project() {}

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
        return station;
    }

    public void setStations(Set<Station> stations) {
        this.station = stations;
    }

    public Set<Factory> getFactories() {
        return factory;
    }

    public void setFactories(Set<Factory> factories) {
        this.factory = factories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

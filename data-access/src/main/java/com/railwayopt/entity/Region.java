package com.railwayopt.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="region")
public class Region {

    @Id
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @OneToMany(mappedBy = "region")
    private Set<Infrastructure> infrastructures;

    public Region(){}

    public Region(Integer id) {
        this.id = id;
    }

    public Region(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<Infrastructure> getInfrastructures(){
        return infrastructures;
    }

    public void setInfrastructures(Set<Infrastructure> infrastructures) {
        this.infrastructures = infrastructures;
    }
}

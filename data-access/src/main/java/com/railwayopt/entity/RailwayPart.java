package com.railwayopt.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="railway_part")
public class RailwayPart {

    @Id
    @Column(name="railway_id")
    private Integer id;
    @Column(name="name")
    private String name;
    @OneToMany(mappedBy = "railway")
    private Set<RailwayBranch> branches;


    public RailwayPart(){}

    public RailwayPart(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RailwayBranch> getBranches() {
        return branches;
    }

    public void setBranches(Set<RailwayBranch> branches) {
        this.branches = branches;
    }
}

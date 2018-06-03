package com.railwayopt.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="railway_branch")
public class RailwayBranch {

    @Id
    @Column(name="branch_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "railway_id")
    private RailwayPart railway;
    @OneToMany(mappedBy = "branch")
    private Set<Station> stations;

    public RailwayBranch(){}

    public RailwayBranch(Integer id, RailwayPart railway) {
        this.id = id;
        this.railway = railway;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RailwayPart getRailway() {
        return railway;
    }

    public void setRailway(RailwayPart railway) {
        this.railway = railway;
    }
}

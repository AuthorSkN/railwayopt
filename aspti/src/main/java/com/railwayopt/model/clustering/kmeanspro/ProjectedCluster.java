package com.railwayopt.model.clustering.kmeanspro;

import com.railwayopt.model.clustering.Cluster;
import com.railwayopt.model.clustering.Element;

import java.util.List;


public class ProjectedCluster extends Cluster{

    private ProjectionPoint projectionCentre;

    public ProjectedCluster(ProjectionPoint initialCentre){
        this.projectionCentre = initialCentre;
    }

    @Override
    public ProjectionPoint getCentre(){
        return projectionCentre;
    }

    public void setCentre(ProjectionPoint centre){
        this.projectionCentre = centre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectedCluster)) return false;
        if (!super.equals(o)) return false;

        ProjectedCluster otherCluster = (ProjectedCluster) o;

        if (!projectionCentre.equals(otherCluster.getCentre())) return false;

        for(Element oel: otherCluster){
            Element el = elementsMap.get(oel.getId());
            if(!oel.equals(el)){
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + projectionCentre.hashCode();
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        ProjectedCluster cluster = (ProjectedCluster)super.clone();
        cluster.projectionCentre = (ProjectionPoint)this.projectionCentre.clone();
        return cluster;
    }


}

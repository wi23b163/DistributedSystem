package org.example.EnergyConsumptionProject.entity;


import java.sql.Timestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

@Entity
@Table(name = "current_percentage")
public class EnergyCurrent {

    @Id
    @Column(name = "hour")
    private Timestamp hour;

    @Column(name = "community_depleted")
    private double communityPool;

    @Column(name = "grid_portion")
    private double gridPortion;

    public EnergyCurrent() {}

    public EnergyCurrent(double communityPool, double gridPortion) {
        this.communityPool = communityPool;
        this.gridPortion = gridPortion;
    }

    public Timestamp getHour() {
        return hour;
    }

    public void setHour(Timestamp hour) {
        this.hour = hour;
    }

    public double getCommunityPool() {
        return communityPool;
    }

    public void setCommunityPool(double communityPool) {
        this.communityPool = communityPool;
    }

    public double getGridPortion() {
        return gridPortion;
    }

    public void setGridPortion(double gridPortion) {
        this.gridPortion = gridPortion;
    }
}



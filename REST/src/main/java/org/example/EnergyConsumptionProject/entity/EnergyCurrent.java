package org.example.EnergyConsumptionProject.entity;

public class EnergyCurrent {
    private double CommunityPool;
    private double GridPortion;

    public EnergyCurrent(double comunityPool) {
        CommunityPool = comunityPool;
    }

    public EnergyCurrent(double comunityPool, double gridPortion) {
        CommunityPool = comunityPool;
        GridPortion = gridPortion;
    }

    public double getCommunityPool() {
        return CommunityPool;
    }
    public void setCommunityPool(double communityPool) { this.CommunityPool = communityPool; }
    public double getGridPortion() {
        return GridPortion;
    }
    public void setGridPortion(double gridPortion) { this.GridPortion = gridPortion; }


}


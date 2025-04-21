package org.example.EnergyConsumptionProject.entity;

public class EnergyHistorical {

    private double produced;    //in kWh
    private double used;        //in kWh
    private double GridUsed;    //in kWh

    public EnergyHistorical(float communityProduced) {
        produced = communityProduced;
    }
    public EnergyHistorical(float communityUsed, float gridUsed) {
        used = communityUsed;
        GridUsed = gridUsed;
    }
    
    public EnergyHistorical(float communityProduced, float communityUsed, float gridUsed) {
        produced = communityProduced;
        used = communityUsed;
        GridUsed = gridUsed;
    }

    public double getProduced() {
        return produced;
    }

    public void setProduced(double produced) { this.produced = produced; }
    public double getUsed() {
        return used;
    }
    public void setUsed(double used) { this.used = used; }
    public double getGridUsed() {
        return GridUsed;
    }
    public void setGridUsed(double gridUsed) { this.GridUsed = gridUsed; }
    
}

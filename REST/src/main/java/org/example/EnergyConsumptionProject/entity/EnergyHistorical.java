package org.example.EnergyConsumptionProject.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "usage_hourly")
public class EnergyHistorical {

    @Id
    @Column(name = "hour")
    private Timestamp hour;

    @Column(name = "community_produced")
    private double produced;

    @Column(name = "community_used")
    private double used;

    @Column(name = "grid_used")
    private double gridUsed;

    public EnergyHistorical() {}

    public EnergyHistorical(double produced, double used, double gridUsed) {
        this.produced = produced;
        this.used = used;
        this.gridUsed = gridUsed;
    }


    public Timestamp getHour() {
        return hour;
    }

    public void setHour(Timestamp hour) {
        this.hour = hour;
    }

    public double getProduced() {
        return produced;
    }

    public void setProduced(double produced) {
        this.produced = produced;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getGridUsed() {
        return gridUsed;
    }

    public void setGridUsed(double gridUsed) {
        this.gridUsed = gridUsed;
    }
}


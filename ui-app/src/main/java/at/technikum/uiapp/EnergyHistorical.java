package at.technikum.uiapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnergyHistorical {
    @JsonProperty("hour") // 🔄 Mapt "hour" aus JSON auf "timestamp"
    private String timestamp;
    private double produced;
    private double used;
    private double gridUsed;

    public String getHour() {
        return timestamp;
    }

    public void setHour(String hour) {
        this.timestamp = hour;
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




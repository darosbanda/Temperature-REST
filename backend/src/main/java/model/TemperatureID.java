package model;

import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class TemperatureID implements Serializable {
    @NotNull
    @JoinColumn(name = "ID", table = "TEMPERATURE_SENSOR", columnDefinition = "int(15)")
    private int sensorID;
    @NotNull
    private Date date;

    public TemperatureID() {
    }

    public TemperatureID(int sensorID, Date date) {
        this.sensorID = sensorID;
        this.date = date;
    }

    public int getSensorID() {
        return sensorID;
    }

    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemperatureID that = (TemperatureID) o;

        return sensorID == that.sensorID && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        int result = sensorID;
        result = 31 * result + date.hashCode();
        return result;
    }
}

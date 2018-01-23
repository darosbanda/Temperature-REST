package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class APIKey {
    @Id
    private String APIKey;

    private int sensorID;


    public APIKey() {
    }

    public APIKey(int sensorID, String APIKey) {
        this.sensorID = sensorID;
        this.APIKey = APIKey;
    }

    public int getSensorID() {
        return sensorID;
    }

    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }
}

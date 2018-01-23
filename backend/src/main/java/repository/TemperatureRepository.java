package repository;

import model.Temperature;
import model.TemperatureID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;


public interface TemperatureRepository extends CrudRepository<Temperature, TemperatureID>{

    @Query("select temp from Temperature temp WHERE temp.date > :fromTime AND temp.sensorID = :sensorID")
    Iterable<Temperature> findAllFrom(@Param("fromTime") Date fromTime, @Param("sensorID") int sensorID);


    Iterable<Temperature> findAllBySensorID(int sensorID);
}

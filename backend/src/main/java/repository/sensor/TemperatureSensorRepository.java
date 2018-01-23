package repository.sensor;

import model.TemperatureSensor;
import org.springframework.data.repository.CrudRepository;

public interface TemperatureSensorRepository extends CrudRepository<TemperatureSensor, Integer> {
}

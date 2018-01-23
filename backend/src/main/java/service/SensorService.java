package service;


import dto.SensorDTO;
import model.TemperatureSensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.sensor.SensorRepository;
import repository.sensor.TemperatureSensorRepository;

@Service
@Transactional
public class SensorService {
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private TemperatureSensorRepository tsRepository;


    public TemperatureSensor createTempSensor(SensorDTO sensorDTO) {
        TemperatureSensor ts = new TemperatureSensor();
        ts.setName(sensorDTO.getName());
        return sensorRepository.save(ts);
    }

    public void deleteSensor(int sensorID) {
        sensorRepository.delete(sensorID);
    }


    public Iterable<TemperatureSensor> getTempSensors() {
        return tsRepository.findAll();
    }
}

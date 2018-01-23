package service;


import dto.TemperatureDTO;
import model.Temperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.KeyRepository;
import repository.TemperatureRepository;

import java.util.Date;


@Service
@Transactional
public class TemperatureService {
    @Autowired
    private TemperatureRepository temperatureRepository;
    @Autowired
    private KeyRepository keyRepository;



    public Temperature addReading(TemperatureDTO reading, int sensorID) {
        Temperature n = new Temperature();
        n.setDate(reading.getDate());
        n.setTemperature(reading.getTemperature());
        n.setSensorID(sensorID);
        return temperatureRepository.save(n);
    }

    public Iterable<Temperature> findAllBySensorID(int sensorID) {
        return temperatureRepository.findAllBySensorID(sensorID);
    }

    public Iterable<Temperature> findAllFrom(Date from, int sensorID) {
        return temperatureRepository.findAllFrom(from, sensorID);
    }

    public boolean keyMatch(String key) {
        return keyRepository.findOne(key) != null;

    }
}

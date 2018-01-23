package controller;

import dto.SensorDTO;
import model.APIKey;
import model.Sensor;
import model.TemperatureSensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.KeyService;
import service.SensorService;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;
    @Autowired
    private KeyService keyService;

    @PostMapping(path = "/temperature")
    @ResponseBody
    public ResponseEntity createSensor(@RequestBody SensorDTO sensorDTO) {
        Sensor s = sensorService.createTempSensor(sensorDTO);
        APIKey k = keyService.addKey(s);
        return new ResponseEntity<>(k.getAPIKey(), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/")
    public ResponseEntity deleteSensor(@PathVariable String key) {
        int sensorID = keyService.deleteKey(key);
        if (sensorID == -1) { //change to Exception
            return new ResponseEntity<>("No Sensor found for APIKey " + key, HttpStatus.NOT_FOUND);
        }
        sensorService.deleteSensor(sensorID);
        return new ResponseEntity<>(key, HttpStatus.OK);
    }

    @GetMapping(path="/temperature")
    public @ResponseBody Iterable<TemperatureSensor> getAllSenors() {
        return sensorService.getTempSensors();
    }
}

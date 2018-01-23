package controller;

import dto.StatDTO;
import dto.TemperatureDTO;
import model.Temperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.KeyService;
import service.StatService;
import service.TemperatureService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(path="sensors/temperatures")
public class TemperatureController {

    @Autowired
    private TemperatureService temperatureService;
    @Autowired
    private StatService statService;
    @Autowired
    private KeyService keyService;



    @PostMapping(path="/")
    @ResponseBody
    public ResponseEntity addNewReading(@RequestBody TemperatureDTO reading) {
        if (keyService.validate(reading.getKey())) {
            int sensorID = keyService.getSensorIDFromKey(reading.getKey());
            Temperature temp = temperatureService.addReading(reading, sensorID);
            return new ResponseEntity<>(temp, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Invalid key", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(path="/{sensorID}")
    public @ResponseBody Iterable<Temperature> getAllTemps(
            @RequestParam(value = "from", required = false) String from,
            @PathVariable("sensorID") String sensorID) {
        if (from != null){
            try {
                Date date = getDate(from);
                return temperatureService.findAllFrom(date, Integer.parseInt(sensorID));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return temperatureService.findAllBySensorID(Integer.parseInt(sensorID));
    }

    @GetMapping(path="/{sensorID}/stats")
    @ResponseBody
    public StatDTO getStats(@RequestParam(value = "from", required = false) String from,
                            @PathVariable("sensorID") String sensorID) {
        if (from != null){
            try {
                Date date = getDate(from);
                return statService.getStatsFrom(date, Integer.parseInt(sensorID));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return statService.getStats(Integer.parseInt(sensorID));
    }

    private Date getDate(String from) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.parse(from);
    }




}

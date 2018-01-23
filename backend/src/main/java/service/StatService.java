package service;

import dto.StatDTO;
import model.Temperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.TemperatureRepository;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@Transactional(readOnly = true)
public class StatService {
    @Autowired
    TemperatureRepository temperatureRepository;

    private double formatValue(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(value));
    }

    public StatDTO getStats(int sensorID) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -24);
        Date from = cal.getTime();
        return getStatsFrom(from, sensorID);
    }

    public StatDTO getStatsFrom(Date from, int sensorID) {
        Iterable<Temperature> temps = temperatureRepository.findAllFrom(from, sensorID);
        StatDTO result = new StatDTO();
        result.setMean(formatValue(getMeanOf(temps)));
        result.setDeviation(formatValue(getDeviationOf(temps)));
        result.setHighest(formatValue(getHighestOf(temps)));
        result.setLowest(formatValue(getLowestOf(temps)));
        result.setFrom(from);
        result.setTo(new Date());
        return result;
    }

    private double getLowestOf(Iterable<Temperature> temps) {
        double lowest = temps.iterator().next().getTemperature();
        for (Temperature temp : temps) {
            if (lowest > temp.getTemperature())
                lowest = temp.getTemperature();
        }
        return lowest;
    }

    private double getHighestOf(Iterable<Temperature> temps) {
        double highest = temps.iterator().next().getTemperature();
        for (Temperature temp : temps) {
            if (highest < temp.getTemperature())
                highest = temp.getTemperature();
        }
        return highest;
    }

    private double getDeviationOf(Iterable<Temperature> temps) {
        int holder = 0;
        double sum = 0;
        double mean = getMeanOf(temps);
        for (Temperature temp : temps) {
            double dif = temp.getTemperature() - mean;
            sum += dif * dif;
            holder++;
        }
        return Math.sqrt(sum/holder);
    }

    private double getMeanOf(Iterable<Temperature> temps) {
        int holder = 0;
        double sum = 0;
        for (Temperature temp : temps) {
            sum += temp.getTemperature();
            holder++;
        }
        return sum / holder;
    }





}

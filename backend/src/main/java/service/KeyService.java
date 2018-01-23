package service;

import model.APIKey;
import model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.KeyRepository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class KeyService {
    @Autowired
    private KeyRepository keyRepository;

    public boolean validate(String key) {
        return keyRepository.findOne(key) != null;

    }

    public int getSensorIDFromKey(String key) {
        return keyRepository.findOne(key).getSensorID();
    }

    public APIKey addKey(Sensor s) {
        String key = createKey();
        APIKey k = new APIKey();
        k.setSensorID(s.getId());
        k.setAPIKey(key);
        return keyRepository.save(k);
    }

    private String createKey () {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            String s = new Date().toString() + UUID.randomUUID().toString();
            m.update(s.getBytes(),0,s.length());
            return new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deleteKey(String key) {
        APIKey k = keyRepository.findOne(key);
        if (k != null) {
            int sensorID = k.getSensorID();
            keyRepository.delete(key);
            return sensorID;
        }
        return -1;
    }
}

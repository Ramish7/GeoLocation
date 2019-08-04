package server;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;

@RestController
public class MainController {
    private class APIInfo {
        public long timestamp;
        public String version;
    }

    private class NearestCity {
        public City city;
        public Double distance;
    }

    @RequestMapping(value = "/api", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public APIInfo home() {
        APIInfo apiInfo = new APIInfo();
        apiInfo.timestamp = System.currentTimeMillis();
        apiInfo.version = "0.1.0";

        return apiInfo;
    }

    @RequestMapping(value = "/api/nearest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public NearestCity nearest(Double latitude, Double longitude) {
        ArrayList<City> cities = getCities();

        Double min = Double.MAX_VALUE;
        City closest = null;
        for (City c : cities) {
            Double dist = c.distance(latitude, longitude);
            if (dist < min) {
                min = dist;
                closest = c;
            }
        }

        NearestCity nc = new NearestCity();
        nc.city = closest;
        nc.distance = min;
        return nc;
    }

    @Cacheable("cities")
    public ArrayList<City> getCities() {
        ArrayList<City> cities = new ArrayList<>();
        try {
            InputStream file = getClass().getClassLoader().getResourceAsStream("cities15000.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                String[] thisLine = st.split("\t");
                City city = new City();
                city.name = thisLine[1];
                city.latitude = Double.parseDouble(thisLine[4]);
                city.longitude = Double.parseDouble(thisLine[5]);
                cities.add(city);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return cities;
    }
}

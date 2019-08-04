package server;

public class City {
    public String name;
    public Double latitude;
    public Double longitude;


    // https://www.geeksforgeeks.org/program-distance-two-points-earth/
    public double distance(double lat, double lon)
    {
        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        Double lon1 = Math.toRadians(longitude);
        Double lon2 = Math.toRadians(lon);
        Double lat1 = Math.toRadians(latitude);
        Double lat2 = Math.toRadians(lat);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r);
    }
}

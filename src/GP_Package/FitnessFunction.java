package GP_Package;

import java.util.ArrayList;
import java.util.List;

public class FitnessFunction {

    public static double Evaluate(String path, ArrayList<City> cities) {
        // string of number are splitted by commas into an array
        // numbers of path correspond to the order in cities in array of cities
        ArrayList<String> paths = new ArrayList<>(List.of(path.split(",")));
        double totalDistance = 0;

        for (int i =0; i<paths.size(); i++) {
            // calculates the distance between the last city and the first city
            if(i==paths.size()-1)
                totalDistance += EuclideanDistance(cities.get(Integer.parseInt(paths.get(i))), cities.get(Integer.parseInt(paths.get(0))));
            else
                totalDistance += EuclideanDistance(cities.get(Integer.parseInt(paths.get(i))), cities.get(Integer.parseInt(paths.get(i + 1))));
        }

        return totalDistance;
    }
// Calculates the distance between two cities using their x and y values.
    private static double EuclideanDistance(City city, City city1) {
        return Math.sqrt(Math.abs(Math.pow(city1.getX_axis() - city.getX_axis(), 2) + Math.pow(city1.getY_axis() - city.getY_axis(), 2)));
    }
}

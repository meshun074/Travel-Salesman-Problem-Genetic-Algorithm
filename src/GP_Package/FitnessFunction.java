package GP_Package;

import java.util.ArrayList;
import java.util.List;

public class FitnessFunction {

    public static double Evalute(String path, ArrayList<City> cities) {
        ArrayList<String> paths = new ArrayList<>(List.of(path.split(",")));
        double totalDistance = 0;

        for (int i =0; i<paths.size()-1; i++) {
            totalDistance += EuclideanDistance(cities.get(Integer.parseInt(paths.get(i))), cities.get(Integer.parseInt(paths.get(i + 1))));
        }
        return totalDistance;
    }

    private static double EuclideanDistance(City city, City city1) {
        return Math.sqrt(Math.abs(Math.pow(city1.getX_axis() - city.getX_axis(), 2) + Math.pow(city1.getY_axis() - city.getY_axis(), 2)));
    }
}

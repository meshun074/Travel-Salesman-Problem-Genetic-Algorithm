package GP_Package;

import java.util.ArrayList;
import java.util.List;

public class FitnessFunction {

    public static double Evaluate(String path, ArrayList<City> cities) {
        // string of number are splitted by commas into an array
        // numbers of path correspond to the order in cities in array of cities
        ArrayList<String> paths = new ArrayList<>(List.of(path.split(",")));
        double totalDistance = 0;
//        if(path.equals("8,9,32,7,1,43,46,39,19,11,38,50,37,10,23,41,0,35,21,47,13,30,2,48,16,17,27,34,49,31,25,12,20,15,4,33,29,6,28,22,44,36,45,18,14,3,40,5,42,24,26")) {
//            cities.forEach(x -> System.out.print(x.getX_axis() + "-" + x.getY_axis() + "  "));
//            System.out.println();
//        }

        for (int i =0; i<paths.size(); i++) {
            // calculates the distance between the last city and the first city
            if(i==paths.size()-1)
                totalDistance += EuclideanDistance(cities.get(Integer.parseInt(paths.get(i))), cities.get(Integer.parseInt(paths.get(0))));
            else
                totalDistance += EuclideanDistance(cities.get(Integer.parseInt(paths.get(i))), cities.get(Integer.parseInt(paths.get(i + 1))));
        }
//        if(path.equals("8,9,32,7,1,43,46,39,19,11,38,50,37,10,23,41,0,35,21,47,13,30,2,48,16,17,27,34,49,31,25,12,20,15,4,33,29,6,28,22,44,36,45,18,14,3,40,5,42,24,26")) {
//            System.out.println("out");
//            cities.forEach(x -> System.out.print(x.getX_axis() + "-" + x.getY_axis() + "  "));
//            System.out.println();
//        }
        return totalDistance;
    }
// Calculates the distance between two cities using their x and y values.
    private static double EuclideanDistance(City city, City city1) {
        return Math.sqrt(Math.abs(Math.pow(city1.getX_axis() - city.getX_axis(), 2) + Math.pow(city1.getY_axis() - city.getY_axis(), 2)));
    }
}

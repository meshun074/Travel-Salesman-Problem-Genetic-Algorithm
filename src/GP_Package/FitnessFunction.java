package GP_Package;

import java.util.ArrayList;

public class FitnessFunction {
    private static double estimatedOptimalDistance=0.0;
    private static double worstTravelledDistance=0.0;
    public static void setEstimatedOptimalDistance(ArrayList<City> cities)
    {
        double[][] distances = new double[cities.size()][cities.size()];
        double distance;
        for(int i =0; i< cities.size()-1; i++)
        {
            for(int k = 0; k<cities.size(); k++) {
                if(k>i) {
                    distance = EuclideanDistance(cities.get(i), cities.get(i + 1));
                    estimatedOptimalDistance = Math.max(distance, estimatedOptimalDistance);
                    distances[i][k]=distance;
                    distances[k][i]=distance;
                }
            }
        }
        // Calculating possible worst distance travelled
        for (double[] doubles : distances) {
            distance = 0.0;
            for (int y = 0; y < distances.length - 1; y++) {
                distance = Math.max(distance, doubles[y]);
            }
            worstTravelledDistance += distance;
        }
    }

    public static double Evalute(ArrayList<City> cities){
         ArrayList<City> path = new ArrayList<>(cities);
         double totalDistance = 0;
         for (int i =0; i< path.size()-1; i++)
         {
             totalDistance+= EuclideanDistance(path.get(i), path.get(i+1));
         }
         return (totalDistance-estimatedOptimalDistance)/(worstTravelledDistance-estimatedOptimalDistance);
    }

    private static double EuclideanDistance(City city, City city1) {
        return Math.sqrt(Math.abs(Math.pow(city1.getX_axis()- city.getX_axis(),2)+Math.pow(city1.getY_axis()- city.getY_axis(), 2)));
    }
}

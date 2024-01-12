package GP_Package;

import java.util.ArrayList;

public class Chromosome {
    private ArrayList<City> path;
    private double fitness;
    private double distance;

    public Chromosome(ArrayList<City> path, double fitness) {
        this.path = new ArrayList<>(path);
        this.fitness = fitness;
        distance = 0.0;
    }
    public Chromosome(ArrayList<City> path, double fitness, double distance) {
        this.path = new ArrayList<>(path);
        this.fitness = fitness;
        this.distance =distance;
    }

    public ArrayList<City> getPath() {
        return path;
    }

    public void setPath(ArrayList<City> path) {
        this.path = path;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double[] fitness) {
        distance=fitness[0];
        this.fitness = fitness[1];
    }

    public double getDistance() {
        return distance;
    }
}

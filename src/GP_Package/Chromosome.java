package GP_Package;

import java.util.ArrayList;

public class Chromosome {
    private ArrayList<City> path;
    private double fitness;

    public Chromosome(ArrayList<City> path, double fitness) {
        this.path = new ArrayList<>(path);
        this.fitness = fitness;
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

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}

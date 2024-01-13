package GP_Package;



public class Chromosome {
    private String path;
    private double fitness;


    public Chromosome(String path, double fitness) {
        this.path = path.replaceAll("[^0-9,]", "");
        this.fitness = fitness;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}

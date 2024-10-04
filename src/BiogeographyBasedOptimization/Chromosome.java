package BiogeographyBasedOptimization;

public class Chromosome {
    private double emm;
    private double imm;
    private String path;
    private double fitness;

    public Chromosome(String path, double fitness, double emm, double imm) {
        // string path (array converted to a string) processed further by remove all characters except 0-9 and comma.
        this.path = path.replaceAll("[^0-9,]", "");
        this.fitness = fitness;
        this.emm =emm;
        this.imm =imm;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path.replaceAll("[^0-9,]", "");
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getEmm() {
        return emm;
    }

    public void setEmm(double emm) {
        this.emm = emm;
    }

    public double getImm() {
        return imm;
    }

    public void setImm(double imm) {
        this.imm = imm;
    }
}

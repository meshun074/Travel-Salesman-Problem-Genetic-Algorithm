package GP_Package;

import java.util.ArrayList;
import java.util.Collections;

public class InitializePopulation {
    private final int populationSize;
    private final ArrayList<City> genes;
    private final ArrayList<Chromosome> population = new ArrayList<>();
    //Generate a random initial population by taking as input the size of the population
    public InitializePopulation(int populationSize, ArrayList<City> genes)
    {
        this.populationSize=populationSize;
        this.genes = new ArrayList<>(genes);
        FitnessFunction.setEstimatedOptimalDistance(genes);
    }

    public ArrayList<Chromosome> generate() {
        for (int i = 0; i < populationSize; i++) {
            Collections.shuffle(genes);
            genes.forEach(x-> System.out.print(x.getX_axis()+" "+x.getY_axis()+" "));
            System.out.println();
            population.add(new Chromosome(genes,0.0));
        }
        return population;
    }
}

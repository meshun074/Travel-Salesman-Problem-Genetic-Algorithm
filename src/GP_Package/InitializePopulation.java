package GP_Package;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class InitializePopulation {
    private final int populationSize;
    private final int genesSize;
    private final ArrayList<Chromosome> population = new ArrayList<>();
    //Generate a random initial population by taking as input the size of the population
    public InitializePopulation(int populationSize, int genesSize)
    {
        this.populationSize=populationSize;
        this.genesSize = genesSize;
    }

    public ArrayList<Chromosome> generate() {
        ArrayList<Integer> genes = new ArrayList<>();
        for(int i = 0; i < genesSize; i++){
            genes.add(i);
        }
        for (int i = 0; i < populationSize; i++) {
            Collections.shuffle(genes);
            population.add(new Chromosome( Arrays.toString(new ArrayList<>(genes).toArray()),0.0));
        }
        return population;
    }
}

package BiogeographyBasedOptimization;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class InitializePopulation {
    private final int populationSize;
    private final int genesSize;
    private final ArrayList<Chromosome> population = new ArrayList<>();
    //Generate a random initial population by taking as input the size of the population and the length of a chromosome
    public InitializePopulation(int populationSize, int genesSize)
    {
        this.populationSize=populationSize;
        this.genesSize = genesSize;
    }

    public ArrayList<Chromosome> generate() {
        ArrayList<Integer> genes = new ArrayList<>();
        // cities are represent as a number from 0 to the number of cities -1
        for(int i = 0; i < genesSize; i++){
            genes.add(i);
        }
        for (int i = 0; i < populationSize; i++) {
            // The order is randomize for each chromosome
            Collections.shuffle(genes);
            // each chromosome is a string of numbers seperated by commas. eg 45,5,33
            population.add(new Chromosome( Arrays.toString(new ArrayList<>(genes).toArray()),0.0,0.0,0.0));
        }
        return population;
    }
}

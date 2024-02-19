import GP_Package.Genetic_Algorithm;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //Start Genetic algorithm by taking as input,
        // population size, Tournament selection rate, crossover rate, mutation rate and
        // number of generations and the file with the encrypted text.
        //Best parameters for Data3: 80,4, 1.0F,0.1F,50000,data3.txt :- Number of generations can be tested further
        //Best parameters for Data1: 100,5, 1.0F,0.1F,1000,data1.txt :- Number of generations can be tested further
        // best parameter data2: 300,3, 1.0F,0.0F,110,"data2.tx
        Genetic_Algorithm ga1 = new Genetic_Algorithm(300,3, 1.0F,0.0F, 1F,110,"data2.txt");
        ga1.start_GA();
    }
}
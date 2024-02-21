import GP_Package.Genetic_Algorithm;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //Start Genetic algorithm by taking as input,
        // population size, Tournament selection rate, crossover rate, mutation rate, elitism and
        // number of generations and the file with the encrypted text.
        // elitism less than 1 wil be converted to % and equal or more than will be taken as the number of chromosomes
        Genetic_Algorithm ga1 = new Genetic_Algorithm(300,3, 1.0F,0.0F, 1F,110,"data2.txt");
        ga1.start_GA();
    }
}
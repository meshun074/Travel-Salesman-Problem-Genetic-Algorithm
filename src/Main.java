import GP_Package.Genetic_Algorithm;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //Start Genetic algorithm by taking as input,
        // population size, Tournament selection rate, crossover rate, mutation rate and
        // number of generations and the file with the encrypted text.
        Genetic_Algorithm ga1 = new Genetic_Algorithm(400,4, 1.0F,0.1F,130,"data1.txt");
        ga1.start_GA();
    }
}
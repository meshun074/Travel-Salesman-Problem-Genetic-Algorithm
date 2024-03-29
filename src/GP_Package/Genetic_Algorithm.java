package GP_Package;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Genetic_Algorithm {
    private List<Chromosome> Population = new ArrayList<>();
    private List<Chromosome> newPopulation;
     private final int popSize;
    private final float crossoverRate;
    private final float mutationRate;
    private final int Gen;
    private final int TSrate;
    private final float elitism;
    private final File file;
    private static int chromosomeLength;
    private final Random r = new Random();
    private final ArrayList<Double> generationDistance;

    public Genetic_Algorithm(int popSize, int TSrate, float crossoverRate, float mutationRate, float elitism, int Gen, String filename) {
        this.popSize = popSize;
        this.TSrate = TSrate;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitism=elitism;
        this.Gen = Gen;
        file = new File(System.getProperty("user.dir") + "/src/" + filename);
        generationDistance=new ArrayList<>();
    }
    public void start_GA() {
        // get the cities points store in data1.txt file
        ArrayList<City> cities = getData(file);
        chromosomeLength = cities.size();
        double averageFitness=0.0;
        //Initialize population
        InitializePopulation pop = new InitializePopulation(popSize, chromosomeLength);
        Population = pop.generate();
        //generate fitness of each chromosome
        for (Chromosome chromosome : Population) {
            chromosome.setFitness(FitnessFunction.Evaluate(chromosome.getPath(), cities));
            averageFitness+=chromosome.getFitness();
        }
        // Sort Population
        Population = new ArrayList<>(sortPop(Population));
        // output current best chromosome
        System.out.println( 0 + " " + Population.get(0).getFitness()+" "+averageFitness/popSize);

        // Drawing initial 2D view of TSP
        City_Line_Chart.DrawChart(new ArrayList<>(List.of(Population.get(0).getPath().split(","))), cities,"Generation 1 2D view of Travel Salesman Problem");
        generationDistance.add(Population.get(0).getFitness());
        // start generation
        for (int i = 1; i <= Gen; i++) {
            newPopulation = new ArrayList<>();
            //implement elitism
            newPopulation.addAll(elitismPop(Population,elitism));
            //Tournament Selection
            List<Chromosome> tempPopulation = new ArrayList<>(tournamentSelect(Population,TSrate));
            //Perform uniformCrossover and mutation
            uniformCrossoverMutation(tempPopulation, crossoverRate, mutationRate);
            //updating population for new generation
            for (int ch = 0; ch < Population.size(); ch++) {
                Population.get(ch).setPath(newPopulation.get(ch).getPath());
            }
            //generate fitness of each chromosome
            averageFitness=0;
            for (Chromosome chromosome : Population) {
                chromosome.setFitness(FitnessFunction.Evaluate(chromosome.getPath(), getData(file)));
                averageFitness+=chromosome.getFitness();
            }
            Population = new ArrayList<>(sortPop(Population));
            // output current best chromosome
            System.out.println( i + " " + Population.get(0).getFitness()+" "+averageFitness/popSize);
            //prints route in the last generation
            if(i==Gen)
                System.out.println( Population.get(0).getPath()+" " + i + " " + Population.get(0).getFitness()+" "+averageFitness/popSize);
            //Store generation best in an array for making future graph.
            generationDistance.add(Population.get(0).getFitness());
        }
        //Draws the final 2D view of the TSP
        City_Line_Chart.DrawChart(new ArrayList<>(List.of(Population.get(0).getPath().split(","))), getData(file), "Generation "+Gen+" 2D view of Travel Salesman Problem");
        //Draws line chart of best distance to generation
        LineChart.DrawChart(generationDistance);
    }

    // Uniform Crossover
    private void uniformCrossoverMutation(List<Chromosome> newPop, float CO_Rate, float M_Rate) {
        int i2 = 1;
        int i1;
        ArrayList<String> c1;
        ArrayList<String> c2;
        ArrayList<String> ch1;
        ArrayList<String> ch2;
        while (i2 < newPop.size()) {
            i1 = i2 - 1;
            // store string of city numbers as list for 2 chromosome
            ch1 = new ArrayList<>(List.of(newPop.get(i1).getPath().split(",")));
            ch2 = new ArrayList<>(List.of(newPop.get(i2).getPath().split(",")));
            //ensures same parents are not used for crossover
            int check = 0;
            while (equalChromosome(newPop.get(i1), newPop.get(i2))) {
                i1 = r.nextInt(newPop.size());
                if (check > newPop.size() / 10)
                    break;
                check++;
            }
            c1 = new ArrayList<>();
            c2 = new ArrayList<>();
//			Crossover rate
            if (Math.random() < CO_Rate) {
                // replace changing cities with _
                for (int c = 0; c < chromosomeLength; c++) {
                    if (Math.random() < 0.5) {
                        c1.add("_");
                        c2.add("_");
                    } else {
                        c1.add(ch1.get(c));
                        c2.add(ch2.get(c));
                    }
                }
                // repair by replace _ positions with numbers from other parents
                repair(c1, ch2);
                repair(c2, ch1);
            }
            //mutation
            //checks if crossover occurred
            if (c1.isEmpty()) c1 = new ArrayList<>(ch1);
            Mutation(c1,M_Rate);
            if (c2.isEmpty()) c2 = new ArrayList<>(ch2);
            Mutation(c2,M_Rate);
            //check if crossover and mutation occur
            //check if new population is full
            //if c1( chromosome) is empty, then neither crossover or mutation occurred
            if (emptyChromosome(newPop, i1, c1)) break;
            if (emptyChromosome(newPop, i2, c2)) break;
            i2 += 2;
        }
    }
    // add the same chromosome if no crossover and mutation occurred
    // add new chromosome
    private boolean emptyChromosome(List<Chromosome> newPop, int i1, ArrayList<String> c1) {
        if (!c1.isEmpty()) {
            newPopulation.add(new Chromosome(Arrays.toString(new ArrayList<>(c1).toArray()), 0.0));
        } else {
            newPopulation.add(newPop.get(i1));
        }
        return Population.size() == newPopulation.size();
    }
    //repair chromosome by replacing _ with numbers from other parents
    private void repair(ArrayList<String> cities, ArrayList<String> path) {
        for (int s = 0; s < cities.size(); s++) {
            if (cities.get(s).equals("_")) {
                for (String p : path) {
                    if (!cities.contains(p)) {
                        cities.set(s, p);
                        break;
                    }
                }
            }
        }
    }
    // checks if chromosome are the same
    private boolean equalChromosome(Chromosome chromosome, Chromosome chromosome1) {
        for (int ch = 0; ch < chromosome.getPath().length(); ch++) {
            if (chromosome.getPath().charAt(ch) != chromosome1.getPath().charAt(ch))
                return false;
        }
        return true;
    }
    //Perform mutation by swapping (1 -1/4 of the total) random cities in the chromosome
    private void Mutation(ArrayList<String> cities, double rate) {
        int index1;
        int index2;
        String c;
        for (int i = 0; i < cities.size(); i++) {
            if(Math.random()<rate){
                //generate 2 random number and swap
                index1 = i;
                do {
                    index2 = r.nextInt(cities.size());
                } while (index1 == index2);
                c = cities.get(index1);
                cities.set(index1, cities.get(index2));
                cities.set(index2, c);
            }
        }
    }

    //read points from data1.txt file and a random city name from cities.txt file
    //Creates an arraylist of all cities in the data1.txt.
    private static ArrayList<City> getData(File file) {
        ArrayList<City> cities = new ArrayList<>();
        String[] texts;
        City city;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {
                texts = sc.nextLine().split(" ");
                city = new City( Double.parseDouble(texts[0]), Double.parseDouble(texts[1]));
                cities.add(city);
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found " + e);
        }
        return cities;
    }
    //sort and return 1% of the best chromosome as string
    private ArrayList<Chromosome> elitismPop(List<Chromosome> population2, float elitism) {
        //elitism 10%
        //int rate = (int) (population2.size() * 0.1);
        int rate = elitism>=1? (int)elitism:(int) (population2.size() * elitism);
        System.out.println(rate);
        int counter = 0;
        ArrayList<Chromosome> elitismPopulation = new ArrayList<>();
        for (Chromosome x : population2) {
            elitismPopulation.add(new Chromosome(x.getPath(), x.getFitness()));
            counter++;
            if (counter == rate) break;
        }
        return elitismPopulation;
    }
    //Sort chromosome according to  fitness
    private List<Chromosome> sortPop(List<Chromosome> popu) {
        List<Chromosome> list = new ArrayList<>(popu);
        list.sort((o1, o2) -> {
            if (o1.getFitness() == o2.getFitness()) return 0;
            else if (o1.getFitness() > o2.getFitness()) return 1;
            return -1;
        });
        return list;
    }
    //Perform tournament selection
    private ArrayList<Chromosome> tournamentSelect(List<Chromosome> population, int rate) {
        ArrayList<Chromosome> TSpop = new ArrayList<>();
        List<Chromosome> temp;
        for (int i = 0; i < Population.size(); i++) {
            temp = new ArrayList<>();
            for (int k = 0; k < rate; k++) {
                temp.add(population.get(r.nextInt(population.size())));
            }
            temp = sortPop(temp);
            TSpop.add(new Chromosome(temp.get(0).getPath(), temp.get(0).getFitness()));
        }
        return TSpop;
    }
    //roulette wheel
    private static ArrayList<Chromosome> rouletteWheelSelection(List<Chromosome> population) {
        ArrayList<Chromosome> selectedPopulation = new ArrayList<>();
        double[] prob = new double[population.size()];
        double total = 0.0;
        double rand;
        //Calculates total fitness
        for (Chromosome chromosome : population) {
            total += chromosome.getFitness();
        }
        // calculate chromosome probability and cumulative probability frequency
        for (int i=0; i<prob.length; i++){
            prob[i]=population.get(i).getFitness()/total;
            if(i>0){
                prob[i]+=prob[i-1];
            }
        }
        ///Simulate turning the wheel for selected chromosome
        for (int k=0; k<population.size(); k++){
            rand=Math.random();
            for (int j=0; j<prob.length; j++){
                if(rand<=prob[j]){
                    selectedPopulation.add(new Chromosome(population.get(j).getPath(),population.get(j).getFitness()));
                    break;
                }
            }
        }
        return selectedPopulation;
    }
}

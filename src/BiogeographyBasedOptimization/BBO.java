package BiogeographyBasedOptimization;

import GP_Package.City;
import GP_Package.City_Line_Chart;
import GP_Package.Genetic_Algorithm.*;
import GP_Package.FitnessFunction;
import GP_Package.LineChart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BBO {
    private static int count =0;
    private List<Chromosome> Population = new ArrayList<>();
    private List<Chromosome> newPopulation;
    private  int Gen;
    private int popSize;
    private int chSize;
    private double mutRate;
    private float eliteRate;
    private final File file;
    private final ArrayList<Double> generationDistance;
    public BBO(int Gen, int popSize,  double mutRate, float eliteRate, String filename){
        this.popSize = popSize;
        this.mutRate = mutRate;
        this.eliteRate = eliteRate;
        this.Gen = Gen;
        file = new File(System.getProperty("user.dir") + "/src/" + filename);
        generationDistance=new ArrayList<>();
    }
    public void startBBO(){
        // get the cities points store in data1.txt file
        ArrayList<City> cities = getData(file);
        chSize = cities.size();
        double averageFitness=0.0;
        //Initialize population
        InitializePopulation pop = new InitializePopulation(popSize, chSize);
        Population = pop.generate();
        //generate fitness of each chromosome
        for (Chromosome chromosome : Population) {
            chromosome.setFitness(FitnessFunction.Evaluate(chromosome.getPath(), getData(file)));
            averageFitness+=chromosome.getFitness();
        }
        // Sort Population
        Population = new ArrayList<>(sortPop(Population));
        // output current best chromosome
        System.out.println(0 + " " + Population.get(0).getFitness() + " " + averageFitness / popSize);
        //Store generation best in an array for making future graph.
        generationDistance.add(Population.get(0).getFitness());
        // Drawing initial 2D view of TSP
        City_Line_Chart.DrawChart(new ArrayList<>(List.of(Population.get(0).getPath().split(","))), cities,"Generation 1 2D view of Travel Salesman Problem");

        for( int g =1; g<=Gen; g++) {

            // calculate emigration and immigration rate
            //Maintain elitism
            newPopulation = elitismPop(Population,eliteRate);

            for (int i = 0; i < popSize; i++) {
                Population.get(i).setEmm((popSize + 1 - (i + 1)) / (popSize + 1.0));
                Population.get(i).setImm(1 - Population.get(i).getEmm());
            }
            //migration Operator
            migration(Population);
            Mutation(Population,mutRate);
            //generate fitness of each chromosome
            for (Chromosome chromosome : Population) {
                chromosome.setFitness(FitnessFunction.Evaluate(chromosome.getPath(), getData(file)));
                averageFitness+=chromosome.getFitness();
            }
            // Sort Population
            Population = new ArrayList<>(sortPop(Population));
            //update population
            //adding better solutions to next generation avoiding duplicates
            boolean check;
            for (Chromosome c1: Population){
                check = true;
                for (Chromosome c2: newPopulation){
                    if(c1.getPath().equals(c2.getPath())){
                        check=false;
                        break;
                    }
                }
                if(check && newPopulation.size()<popSize){
                    newPopulation.add(new Chromosome(c1.getPath(),c1.getFitness(),c1.getEmm(),c1.getImm()));
                }
                if (newPopulation.size()==popSize)
                    break;
            }
            //check in we have the right size of population.
            if(newPopulation.size()<popSize){
                for (Chromosome c1: Population){
                    newPopulation.add(new Chromosome(c1.getPath(),c1.getFitness(),c1.getEmm(),c1.getImm()));
                    if (newPopulation.size()==popSize)
                        break;
                }
            }
            Population = newPopulation;
            // Sort Population
            Population = new ArrayList<>(sortPop(Population));
            // output current best chromosome
            System.out.println(g + " " + Population.get(0).getFitness() + " " + averageFitness / popSize);
            //Store generation best in an array for making future graph.
            generationDistance.add(Population.get(0).getFitness());

        }
        //Draws the final 2D view of the TSP
        City_Line_Chart.DrawChart(new ArrayList<>(List.of(Population.get(0).getPath().split(","))), getData(file), "Generation "+Gen+" 2D view of Travel Salesman Problem");
        //Draws line chart of best distance to generation
        LineChart.DrawChart(generationDistance);
    }
    private ArrayList<Chromosome> elitismPop(List<Chromosome> pop, float elitism) {
        int rate = elitism>=1? (int)elitism:(int) (pop.size() * elitism);
        int counter = 0;
        ArrayList<Chromosome> elitismPopulation = new ArrayList<>();
        for (Chromosome x : pop) {
            elitismPopulation.add(new Chromosome(x.getPath(), x.getFitness(), x.getEmm(), x.getImm()));
            counter++;
            if (counter == rate) break;
        }
        return elitismPopulation;
    }
    private void Mutation(List<Chromosome> pop, double mutRate){
        ArrayList<String> ch;
        for(int i =0; i< popSize; i++) {
            ch = new ArrayList<>(List.of(pop.get(i).getPath().split(",")));
            MutationOperator(ch,mutRate);
            pop.get(i).setPath(Arrays.toString(new ArrayList<>(ch).toArray()));
        }
    }
    private void MutationOperator(ArrayList<String> cities, double rate) {
        Random r = new Random(System.currentTimeMillis());
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
    private void migration(List<Chromosome> pop){
        ArrayList<String> imm;
        ArrayList<String> emm;
        String immValue;
        String emmValue;
        double rand;
        double selectValue;
        int selectIndex =1;
        for(int i =0; i< popSize; i++){
            for(int c =0; c<chSize; c++){
                if(Math.random()<pop.get(i).getImm()){
                    rand = Math.random()*(popSize/2.0);
                    selectValue =0;
                    for(int p =0; p<popSize; p++){
                        selectValue += pop.get(p).getEmm();
                        if(rand>selectValue&&selectIndex<popSize){
                            selectIndex++;
                        }else {
                            count++;
                            if(i!=p){
                                imm = new ArrayList<>(List.of(pop.get(i).getPath().split(",")));
                                emm = new ArrayList<>(List.of(pop.get(p).getPath().split(",")));
                                if(!imm.get(c).equals(emm.get(c))){
//                                    System.out.println("c "+c);
//                                    System.out.println("imm "+pop.get(i).getPath());
//                                    System.out.println("emm "+pop.get(p).getPath());
//                                    System.out.println("imm "+imm);
//                                    System.out.println("emm "+emm);
                                    emmValue = emm.get(c);
                                    immValue = imm.get(c);
                                    imm.set(imm.indexOf(emmValue), immValue);
                                    imm.set(c,emmValue);
                                    pop.get(i).setPath(Arrays.toString(new ArrayList<>(imm).toArray()));
//                                    System.out.println("imm "+pop.get(i).getPath());
//                                    System.out.println("emm "+pop.get(p).getPath());
//                                    System.out.println("imm "+imm);
//                                    System.out.println("emm "+emm);
//                                    System.exit(1);

                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
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
    private List<Chromosome> sortPop(List<Chromosome> popu) {
        List<Chromosome> list = new ArrayList<>(popu);
        list.sort((o1, o2) -> {
            if (o1.getFitness() == o2.getFitness()) return 0;
            else if (o1.getFitness() > o2.getFitness()) return 1;
            return -1;
        });
        return list;
    }
}

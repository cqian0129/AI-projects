/**
 * Created by chengqian on 11/14/16.
 */
import java.io.*;
import java.util.*;

/***********************************************************************/

//this program finds optimal value of sine function using genetic algorithm.
//llustrates float values, negative function values, multiple optimal values,
//and minimization

public class Optimize {
    private int populationSize;         //population size
    private int stringLength;           //string length
    private int numberIterations;       //number of iterations
    private double crossoverRate;       //crossover rate
    private double mutationRate;        //mutation rate
    private Random random;              //random number generator

    private int[][] population;         //population of strings
    private double[] fitnessValues;     //fitness values of strings

    /***********************************************************************/

    //Constructor of Optimize class
    public Optimize(int populationSize, int stringLength, int numberIterations,
                    double crossoverRate, double mutationRate, int seed) {
        //set genetic algorithm parameters
        this.populationSize = populationSize;
        this.stringLength = stringLength;
        this.numberIterations = numberIterations;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.random = new Random(seed);

        //create population and fitness arrays
        this.population = new int[populationSize][stringLength];
        this.fitnessValues = new double[populationSize];
    }

    /***********************************************************************/

    //Method initializes population
    private void initialize() {
        //initialize strings with random 0/1
        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j < stringLength; j++)
                population[i][j] = random.nextInt(2);

        //initialize fitness values are zero
        for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = 0;
    }

    /***********************************************************************/

    //Method performs crossover operation
    private void crossover() {
        //go thru each string
        for (int i = 0; i < populationSize; i++) {
            //if crossover is performed
            if (random.nextDouble() < crossoverRate) {
                //choose another string
                int j = random.nextInt(populationSize);

                //choose crossover point
                int cut = random.nextInt(stringLength);

                //crossover bits of the two strings
                for (int k = cut; k < stringLength; k++) {
                    int temp = population[i][k];
                    population[i][k] = population[j][k];
                    population[j][k] = temp;
                }
            }
        }
    }

    /***********************************************************************/

    //Method performs mutation operation
    private void mutate() {
        //go thru each bit of each string
        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j < stringLength; j++) {
                //if mutation is performed
                if (random.nextDouble() < mutationRate) {
                    //flip bit from 0 to 1 or 1 to 0
                    if (population[i][j] == 0)
                        population[i][j] = 1;
                    else
                        population[i][j] = 0;
                }
            }
    }

    /***********************************************************************/

    //Method performs reproduction operation
    private void reproduce() {
        //find fitness values of all strings
        computeFitnessValues();

        //create array for next generation
        int[][] nextGeneration = new int[populationSize][stringLength];

        //repeat population number of times
        for (int i = 0; i < populationSize; i++) {
            //select a string from current generation based on fitness
            int j = select();

            //copy selected string to next generation
            for (int k = 0; k < stringLength; k++)
                nextGeneration[i][k] = population[j][k];
        }

        //next generation becomes current generation
        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j < stringLength; j++)
                population[i][j] = nextGeneration[i][j];
    }

    /***********************************************************************/

    //Method computes fitness values of all strings
    private void computeFitnessValues() {
        //compute fitness values of strings
        for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitness(population[i]);

        //accumulate fitness values
        for (int i = 1; i < populationSize; i++)
            fitnessValues[i] = fitnessValues[i] + fitnessValues[i - 1];

        //normalize accumulated fitness values
        for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitnessValues[i] / fitnessValues[populationSize - 1];
    }

    /***********************************************************************/

    //Method selects a string based on fitness values
    private int select() {
        //create a random number between 0 and 1
        double value = random.nextDouble();

        //determine on which normalized accumulated fitness value it falls
        int i;
        for (i = 0; i < populationSize; i++)
            if (value <= fitnessValues[i])
                break;

        //return the string where the random number fell
        return i;
    }

    /***********************************************************************/

    //Method finds the best solution
    private void solution() {
        //compute fitness  values of strings
        for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitness(population[i]);

        //find the string with best fitness value
        int best = 0;
        for (int i = 0; i < populationSize; i++)
            if (fitnessValues[i] > fitnessValues[best])
                best = i;

        //convert best string from binary to float
        double value = convert(population[best]);

        //display float value of string
        System.out.println(value);
    }

    /***********************************************************************/

    //Method computes fitness value of a string (application specific)
    private double fitness(int[] string) {
        //convert strign from binary to float
        double value = convert(string);

        //compute fitness value using sinx + 1
        value = Math.sin(value) + 1;

        //return final fitness value
        return value;
    }

    /***********************************************************************/

    //Method converts a string from binary to float (application specific)
    private double convert(int[] string)
    {
        //initial value and power
        double value = 0;
        int power = 1;

        //go thru bits right to left and accumulates powers of 2
        for (int i = stringLength-1; i >= 0; i++)
        {
            value = value + string[i]*power;
            power = power*2;
        }

        //convert to float value between 0 and 2pi
        value = 2*Math.PI/Math.pow(2, stringLength)*value;

        //return float value
        return value;
    }
}

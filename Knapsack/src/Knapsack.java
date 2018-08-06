/**
 * Created by chengqian on 11/14/16.
 */
import java.io.*;
import java.util.*;

/***********************************************************************/

//This programs solves integer knapsack problem using genetic algorithm
public class Knapsack
{
    private int[][] table;              //weights and values of items
    private int numberItems;            //number of items
    private int maximumWeight;          //maximum weight

    private int populationSize;         //population size
    private int stringLength;           //string length
    private int numberIterations;           //number of iterations
    private double crossoverRate;           //crossover rate
    private double mutationRate;            //mutation rate
    private Random random;                  //random number generator

    private int[][] population;             //populatioin of strings
    private double[] fitnessValues;        //fitness values of strings

    /***********************************************************************/

    //Constructor of Knapsack class
    public Knapsack(int[][] table, int numberItems, int maximumWeight)
    {
        //set knapsack data
        this.table = table;
        this.numberItems = numberItems;
        this.maximumWeight = maximumWeight;
    }

    /***********************************************************************/

    //Method sets parameters of genetic algorithm
    public void setParameters(int populationSize, int stringLength, int numberIterations, double crossoverRate, double mutationRate, int seed)
    {
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

    //Method executes genetic algorithm
    public void solve()
    {
        //initialize population
        initialize();

        //create generations
        for (int i = 0; i < numberIterations; i++)
        {
            //crossover strings
            crossover();

            //mutate strings
            mutate();

            //reproduce strings
            reproduce();
        }

        //choose best string
        solution();
    }

    /***********************************************************************/

    //Method initializes population
    private void initialize()
    {
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
    private void crossover()
    {
        //go thru each string
        for (int i = 0; i < populationSize; i++)
        {
            //if crossover is performed
            if (random.nextDouble() < crossoverRate)
            {
                //choose another string
                int j = random.nextInt(populationSize);

                //choose crossover point
                int cut = random.nextInt(stringLength);

                //crossover bits of the two strings
                for (int k = cut; k < stringLength; k++)
                {
                    int temp = population[i][k];
                    population[i][k] = population[j][k];
                    population[j][k] = temp;
                }
            }
        }
    }

    /***********************************************************************/

    //Method performs mutation operation
    private void mutate()
    {
        //go thru each bit of each string
        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j < stringLength; j++)
            {
                //if mutation is performed
                if (random.nextDouble() < mutationRate)
                {
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
    private void reproduce()
    {
        //find fitness values of all strings
        computeFitnessValues();

        //create array for next generation
        int[][] nextGeneration = new int[populationSize][stringLength];

        //repeat population number of times
        for (int i = 0; i < populationSize; i++)
        {
            //select a string from current generation based on fitness
            int j = select();
            //System.out.println(populationSize);
            //copy selected string to next generation
            for (int k = 0; k < stringLength; k++) {
                //System.out.println("k  " + k);
                //System.out.println("i  " + i);
                //System.out.println("j  " + j);

                nextGeneration[i][k] = population[j][k];
            }
        }

        //next generation becomes current generation
        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j < stringLength; j++)
                population[i][j] = nextGeneration[i][j];
    }

    /***********************************************************************/

    //Method computes fitness values of all strings
    private void computeFitnessValues()
    {
        //compute fitness values of strings
        for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitness(population[i]);

        //accumulate fitness values
        for (int i = 1; i < populationSize; i++)
            fitnessValues[i] = fitnessValues[i] + fitnessValues[i-1];

        //normalize accumulated fitness values
        for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitnessValues[i]/fitnessValues[populationSize-1];
    }

    /***********************************************************************/

    //Method selects a string based on fitness values
    private int select()
    {
        //create a random number between 0 and 1
        double value = random.nextDouble();
        //System.out.println(populationSize);

        //determine on which normalized accumulated fitness value it falls
        int i;
        for (i = 0; i < populationSize-1; i++)
            if (value <= fitnessValues[i])
                break;

        //System.out.println("i" + i);
        //return the string where the random number fell
        return i;
    }

    /***********************************************************************/

    //Method finds the best solution
    private void solution()
    {
        //compute fitness  values of strings
        for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitness(population[i]);

        //find the stirng with best fitness value
        int best = 0;
        for (int i = 0; i < populationSize; i++)
            if (fitnessValues[i] > fitnessValues[best])
                best = i;

        //display the best string
        display(population[best]);
    }

    /***********************************************************************/

    //Method computes fitness value of a string (application specitic)
    private double fitness(int[] string)
    {
        //initialize weight and value
        int weight = 0;
        int value = 0;

        //go thru stirng
        for (int i = 0; i < stringLength; i++)
            //add up values and weights of selected items
            if (string[i] == 1)
            {
                weight += table[i][1];
                value += table[i][2];
            }

        //if total weight does not exceed maximum weight fitness is total value
        if (weight <= maximumWeight)
            return value;
        //if total weight exceeds maximum weight fitness is zero
        else
            return 0;
    }

    /***********************************************************************/

    //Method displays a string (application specitic)
    private void display(int[] string)
    {
        int weighttotal = 0;
        int valuetotal = 0;
        //go thru string
        for (int i = 0; i < stringLength; i++)
            //if item is selected print its weight and value
            if (string[i] == 1) {
                System.out.println(table[i][0] + " " + table[i][1] + " " + table[i][2]);
                weighttotal += table[i][1];
                valuetotal += table[i][2];
            }

        System.out.println(weighttotal);
        System.out.println(valuetotal);
    }

    /***********************************************************************/


}

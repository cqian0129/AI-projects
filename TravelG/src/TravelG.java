/**
 * Created by chengqian on 11/22/16.
 */
import java.io.*;
import java.util.*;

public class TravelG {
    private int[][] matrix;
    private int numberVertices;
    private int maximumEdge;

    private int populationSize;
    private int stringLength;
    private int numberIterations;
    private double crossoverRate;
    private double mutationRate;
    private Random random;

    private int[][] population;
    private double[] fitnessValues;

    public TravelG(int[][] matrix, int numberVertices){
        this.matrix = matrix;
        this.numberVertices = numberVertices;

        maximumEdge = Integer.MIN_VALUE;
        for (int i = 0; i < numberVertices; i++)
            for (int j = 0; j < numberVertices; j++)
                if (matrix[i][j] > maximumEdge)
                    maximumEdge = matrix[i][j];
    }

    public void setParameters(int populationSize, int stringLength, int numberIterations, double crossoverRate, double mutationRate, int seed)
    {
        this.populationSize = populationSize;
        this.stringLength = stringLength;
        this.numberIterations = numberIterations;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.random = new Random(seed);

        this.population = new int[populationSize][stringLength];
        this.fitnessValues = new double[populationSize];
    }

    public void solve()
    {
        initialize();

        for (int i = 0; i < numberIterations; i++)
        {
            //crossover strings
            crossover();

            //mutate strings
            mutate();

            //reproduce strings
            reproduce();
        }

        solution();
    }

    private void initialize()
    {
        for (int i = 0; i < populationSize; i++)
            randomPermutation(population[i]);

        for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = 0;
    }

    private void randomPermutation(int[] string)
    {
        for (int i = 0; i < stringLength; i++)
            string[i] = i+1;

        for (int i = stringLength-1; i >= 0; i--)
        {
            int j = random.nextInt(i+1);
            int temp = string[i];
            string[i] = string[j];
            string[j] = temp;
        }
    }

    private void crossover()
    {
        for (int i = 0; i < populationSize; i++)
        {
            if (random.nextDouble() < crossoverRate)
            {
                int j = random.nextInt(populationSize);

                int cut = random.nextInt(stringLength);

                int[] copy = new int[stringLength];
                for (int k = 0; k < stringLength; k++)
                    copy[k] = population[i][k];

                for (int k = cut; k < stringLength; k++)
                    swap(population[i][k], population[j][k], population[i]);

                for (int k = cut; k < stringLength; k++)
                    swap(copy[k], population[j][k], population[j]);
            }
        }
    }

    private void swap(int a, int b, int[] string)
    {
        int i;
        for (i = 0; i < stringLength; i++)
            if (string[i] == a)
                break;

        int j;
        for (j = 0; j < stringLength; j++)
            if (string[j] == b)
                break;

        int temp = string[i];
        string[i] = string[j];
        string[j] = temp;
    }

    private void mutate()
    {
        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j < stringLength; j++)
            {
                //if mutation is performed
                if (random.nextDouble() < mutationRate)
                {
                    int k = random.nextInt(stringLength);

                    int temp = population[i][j];
                    population[i][j] = population[i][k];
                    population[i][k] = temp;
                }
            }
    }

    private void reproduce()
    {
        computeFitnessValues();

        int[][] nextGeneration = new int[populationSize][stringLength];

        for (int i = 0; i < populationSize; i++)
        {
            int j = select();

            for (int k = 0; k < stringLength; k++)
                nextGeneration[i][k] = population[j][k];
        }

        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j < stringLength; j++)
                population[i][j] = nextGeneration[i][j];
    }

    private void computeFitnessValues()
    {
        //compute fitness values of strings
        for (int i = 0; i < populationSize; i++) {

            fitnessValues[i] = fitness(population[i]);
        }

        //accumulate fitness values
        for (int i = 1; i < populationSize; i++)
            fitnessValues[i] = fitnessValues[i] + fitnessValues[i-1];

        //normalize accumulated fitness values
        for (int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitnessValues[i]/fitnessValues[populationSize-1];
    }

    private int select()
    {
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

    private void solution()
    {
        //compute fitness  values of strings
        for (int i = 0; i < populationSize; i++) {

            fitnessValues[i] = fitness(population[i]);
        }

        //find the stirng with best fitness value
        int best = 0;
        for (int i = 0; i < populationSize; i++)
            if (fitnessValues[i] > fitnessValues[best])
                best = i;

        //display the best string
        display(population[best]);
    }

    private double fitness(int[] string)
    {
        double sum = 0;
        for (int i = 0; i < stringLength; i++) {
            sum += matrix[string[i] - 1][string[(i + 1) % stringLength] - 1];

            //System.out.println("i   " + i + "stringi   " + string[i]);
        }

        return numberVertices*maximumEdge - sum;
    }

    private void display(int[] string)
    {
        for (int i = 0; i < stringLength; i++)
            System.out.print(string[i] + " ");
        System.out.print(string[0] + " ");

        double sum = 0;
        for (int i = 0; i < stringLength; i++)
            sum += matrix[string[i]-1][string[(i+1)%stringLength]-1];

        System.out.println(": " + sum);
    }
}

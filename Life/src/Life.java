/**
 * Created by chengqian on 12/20/16.
 */
import java.util.Random;

public class Life {
    private final int EMPTY = 0;        //empty location
    private final int OCCUPIED = 1;     //occupied location

    private int[][] array;              //array of empty/occupied locations
    private int size;                   //size of array
    private int iterations;              //number of iterations
    private double density;             //initial density of occupied locations
    private Random random;              //random number generator
    private DrawLife drawer;            //drawing object

    //Constructor of Life class
    public Life (int size, int iterations, double density, int seed)
    {
        this.array = new int[size][size];
        this.size = size;
        this.iterations = iterations;
        this.density = density;
        this.random = new Random(seed);
        this.drawer = new DrawLife(array, size);
    }

    //Method runs simulation
    public void run()
    {
        int[][] next = new int[size][size];

        initialize();

        for (int n = 0; n < iterations; n++)
        {
            draw();

            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    next[i][j] = update(i, j);

            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    array[i][j] = next[i][j];
        }
    }

    //Method initializes array
    private void initialize()
    {
        int option = 2;

        if (option == 1)
        {
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                {
                    if (random.nextDouble() < density)
                        array[i][j] = OCCUPIED;
                    else
                        array[i][j] = EMPTY;
                }
        }
        else
        {
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    array[i][j] = EMPTY;

            int[][] pattern = {
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 1},
                    {0, 0, 0, 0, 0}
            };

            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 5; j++)
                    array[size/2-2+i][size/2-2+j] = pattern[i][j];
        }
    }

    //Method determines how a location changes depending on its neighborhood
    private int update(int i, int j)
    {
        int count = 0;

        if (array[(i-1+size)%size][(j-1+size)%size] == OCCUPIED) count++;
        if (array[(i-1+size)%size][j] == OCCUPIED) count++;
        if (array[(i-1+size)%size][(j+1)%size] == OCCUPIED) count++;
        if (array[i][(j-1+size)%size] == OCCUPIED) count++;
        if (array[i][(j+1)%size] == OCCUPIED) count++;
        if (array[(i+1)%size][(j-1+size)%size] == OCCUPIED) count++;
        if (array[(i+1)%size][j] == OCCUPIED) count++;
        if (array[(i+1)%size][(j+1)%size] == OCCUPIED) count++;

        if (count <= 1)
            return EMPTY;
        else if (count == 2)
            return array[i][j];
        else if (count == 3)
            return OCCUPIED;
        else
            return EMPTY;
    }

    //Method draws array of locations
    private void draw()
    {
        drawer.repaint();

        pause(1000);
    }

    private void pause(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException e)
        {
            System.exit(0);
        }
    }
}

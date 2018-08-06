/**
 * Created by chengqian on 12/13/16.
 */
import java.util.*;

/*******************************************************************************************/

public class Majority {
    private final int PLUS = 1;         //opinion one
    private final int MINUS = 2;        //opinion two

    private int[][] array;              //array of opinions
    private int size;                   //size of array
    private int iterations;             //number of iterations
    private double density;             //initial density of plus opinion
    private Random random;              //random number generator
    private DrawOpinion drawer;         //drawing object

    private int sizeList = 8;          //stubborn agent size

    List iList = new ArrayList();
    List jList = new ArrayList();

    private int plusCount = 0;
    private int minusCount = 0;
    /*******************************************************************************************/

    //Constructor of Majority class
    public Majority(int size, int iterations, double density, int seed)
    {
        this.array = new int[size][size];       //create array
        this.size = size;                       //set size
        this.iterations = iterations;           //set iterations
        this.density = density;                 //set density
        this.random = new Random(seed);         //create random number generator
        this.drawer = new DrawOpinion(array, size);     //create drawing object

        for (int i = 0; i < sizeList; i++){
            iList.add(random.nextInt(size));
            jList.add(random.nextInt(size));
        }

    }

    /*******************************************************************************************/

    //Method runs simulation
    public void run()
    {
        //initialize opinions
        initialize();

        //run iterations
        for (int n = 0; n < iterations; n++)
        {
            //draw array
            draw();

            //update population
            for (int m = 0; m < size*size; m++)
            {
                //pick an agent randomly
                int i = random.nextInt(size);
                int j = random.nextInt(size);

                if (iList.contains(i) && jList.contains(j))
                    array[i][j] = MINUS;
                else
                    array[i][j] = imitate(i, j);
            }
        }

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                if (array[i][j] == PLUS)
                    plusCount++;
                else if (array[i][j] == MINUS)
                    minusCount++;
            }

        System.out.println("Plus:" + plusCount);
        System.out.println("Minus:" + minusCount);
    }

    /*******************************************************************************************/

    //Method initializes opinions
    private void initialize()
    {

        //go thru all agents
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (iList.contains(i) && jList.contains(j))
                    array[i][j] = MINUS;
                else if (random.nextDouble() < density)  //random number <= density 0.6
                    array[i][j] = PLUS;
                else
                    array[i][j] = MINUS;
            }
    }

    /*******************************************************************************************/

    private int imitate(int i, int j)
    {
        int plus = 0, minus = 0;

        if (array[(i+1)%size][j] == PLUS)           //south
            plus++;
        else
            minus++;

        if (array[(i-1+size)%size][j] == PLUS)          //north
            plus++;
        else
            minus++;

        if (array[i][(j+1)%size] == PLUS)          //east
            plus++;
        else
            minus++;

        if (array[i][(j-1+size)%size] == PLUS)          //south
            plus++;
        else
            minus++;

        if (array[i][j] == PLUS)
            plus++;
        else
            minus++;

        if (plus > minus)
            return PLUS;
        else
            return MINUS;

    }

    /*******************************************************************************************/

    //Method draws array of opinions
    private void draw()
    {
        drawer.repaint();		//repaint array



        pause(1);				//pause for some time
    }

    /*******************************************************************************************/

    //Method pauses program for some milliseconds
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

    /*******************************************************************************************/
}

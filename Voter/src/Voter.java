import java.util.Random;

/*******************************************************************************************/

public class Voter {
	private final int PLUS = 1;			//opinion one
	private final int MINUS = 2;		//opinion two
	
	private int[][] array;				//array of opinions
	private int size;					//size of array
	private int iterations;				//number of iterations
	private double density;				//initial density of plus opinion
	private Random random;				//random number generator
	private DrawOpinion drawer;			//drawing object
	
	/*******************************************************************************************/
	
	//Constructor of Voter class
	public Voter(int size, int iterations, double density, int seed)
	{
		this.array = new int[size][size];	//create array
		this.size = size;					//set size
		this.iterations = iterations;		//set iterations
		this.density = density;				//set density
		this.random = new Random(seed);		//create random number generator
		this.drawer = new DrawOpinion(array, size);	//create drawing object
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
				
				//change opinion of agent to a neighbor's opinion
				array[i][j] = imitate(i, j);
			}
		}
	}
	
	/*******************************************************************************************/
	
	//Method initializes opinions
	private void initialize()
	{
		//go thru all agents
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
			{
				//assign plus or minus opinion
				if (random.nextDouble() < density)//random number <= density 0.6
					array[i][j] = PLUS;
				else
					array[i][j] = MINUS;
			}
	}
	
	/*******************************************************************************************/
	
	//Method finds the opinion of a random neighbor
	private int imitate(int i, int j)
	{
		//pick a random neighbor
		double r = random.nextDouble();
		
		if (r < 0.25)
			return array[(i+1)%size][j];			//opinion of south neighbor
		else if (r < 0.5)
			return array[(i-1+size)%size][j];		//opinion of north neighbor
		else if (r < 0.75)
			return array[i][(j+1)%size];			//opinion of east neighbor
		else
			return array[i][(j-1+size)%size];		//opinion of south neighbor
	}
	
	/*******************************************************************************************/

	//Method draws array of opinions
	private void draw()
	{
		drawer.repaint();		//repaint array
		
		pause(100);				//pause for some time
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

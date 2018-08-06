/**
 * Created by chengqian on 10/15/16.
 */
import java.util.LinkedList;
import java.util.Random;

public class Travel
{
    private class Path
    {
        private LinkedList<Integer> list;		//vertices in path
        private int cost;

        //Constructor of path class
        private Path()
        {
            list = new LinkedList<Integer>();	//empty list of vertices
            cost = 0;
        }

        //Copy constructor
        private Path(Path other)
        {
            list = new LinkedList<Integer>();		//empty lsit of vertices

            for (int i = 0; i < other.list.size(); i++)
                list.addLast(other.list.get(i));                   //111111

            cost = other.cost;						//copy cost
        }

        //Method adds vertex to path
        private void add(int vertex, int weight)
        {
            list.addLast(vertex);					//add vertex at the end                  //1111111111
            cost += weight;							//increment cost
        }

        //Method finds last vertex of path
        private int last()
        {
            return list.getLast();					//return last vertex
        }

        //Method finds cost of path
        private int cost()
        {
            return cost;							//return cost
        }

        //Method finds length of path
        private int size()
        {
            return list.size();
        }

        //Method decides whether path contains a given vertex
        private boolean contains(int vertex)
        {
            for (int i = 0; i < list.size(); i++)	//compare vertex with
                if (list.get(i) == vertex)			//vertices of path
                    return true;

            return false;
        }

        //Method displays path and its cost
        private void display()
        {
            System.out.print("Cycle: ");
            for (int i = 0; i < list.size(); i++)		//print path
                System.out.print(list.get(i) + 1 + " ");
            System.out.println();
            System.out.println("Miles: " + cost);			//print cost



        }
    }
    private int size; 			//number of vertices of graph
    private int[][] matrix;		//adjacency matrix of graph
    private int initial;		//starting/ending vertex

    public long startTime;
    public long endTime;
    public long totalTime;

    public int count_state;
    public int count_cycle;

    //Constructor of travel class
    public Travel(int vertices, int[][] edges)
    {
        count_state = 0;
        endTime = 0;
        totalTime = 0;
        count_cycle = 0;

        size = vertices;		//assign vertices

        matrix = new int[size][size];		//initialize adjacency matrix
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = 0;

        for (int i = 0; i < edges.length; i++)
        {
            int u = edges[i][0] - 1;			//place weights in adjacency
            int v = edges[i][1] - 1;			//matrix using edges
            int weight = edges[i][2];
            matrix[u][v] = weight;
            matrix[v][u] = weight;
        }

        initial = edges[0][0] - 1;				//pick a starting/ending vertex
    }

    //Method finds shortest cycle
    public void solve()
    {


        Path shortestPath = null;		//initialize shortest path
        int minimumCost = Integer.MAX_VALUE;	//and minimum cost

        LinkedList<Path> list = new LinkedList<Path>();		//list of paths

        Path path = new Path();			//create initial path and add to list
        path.add(initial, 0);
        list.addFirst(path);

        while (!list.isEmpty())			//while list has paths
        {
            path = list.removeFirst();	//remove first path
            count_cycle++;

            if (complete(path))			//if path is complete
            {
                if(path.cost() < minimumCost)	//if cost is less than minimum
                {
                    minimumCost = path.cost(); 	//update minimum
                    shortestPath = path;		//update path
                }


            }
            else
            {
                LinkedList<Path> children = generate(path);		//generate children of path

                for (int i = 0; i < children.size(); i++) {
                    list.addFirst(children.get(i));            //add children to beginning of list

                }
            }
        }

        if (shortestPath == null)							//if there is no cycle
            System.out.println("no solution");				//then there is no solution
        else
            shortestPath.display();								//otherwise display shortest cycle

        //System.out.println("states number: " + count_state);
        //System.out.println("cycles number: " + count_cycle);



    }

    //Method generates children of path
    private LinkedList<Path> generate(Path path)
    {
        LinkedList<Path> children = new LinkedList<Path>();		//children list find last vertex of path

        int lastVertex = path.last();

        for (int i = 0; i < size; i++)			//go thru all vertices
        {
            if (matrix[lastVertex][i] != 0) 		//if vertex is neighbor
            {
                if (i == initial)				//if vertex is initial vertex
                {
                    if(path.size() == size)		//if path has size vertices
                    {
                        Path child = new Path(path); 		//create copy of path

                        child.add(i, matrix[lastVertex][i]);		//add vertex to path

                        children.addLast(child);				//add extended path to children list
                        count_cycle++;
                    }
                }												//if vertex is not initial vertex
                else
                {
                    if (!path.contains(i))						//if vertex is not in path
                    {
                        Path child = new Path(path);			//create copy of path

                        child.add(i, matrix[lastVertex][i]);
                        //add extended path to children list
                        children.addLast(child);
                        count_state++;
                    }
                }
            }
        }

        return children;										//return children lsit
    }

    //Method decides whether a path is complete
    boolean complete(Path path)
    {
        return path.size() == size + 1;			//check path has size+1 number of vertices
    }
}
